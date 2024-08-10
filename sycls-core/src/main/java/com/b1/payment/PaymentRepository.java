package com.b1.payment;

import com.b1.exception.customexception.SeatGradeAlreadySoldOutException;
import com.b1.exception.errorcode.SeatGradeErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "Payment Repository")
@Repository
@RequiredArgsConstructor
public class PaymentRepository {

    private final long LOCK_EXPIRATION_TIME = 10; // 10초 동안 잠금 유지
    private final long RESERVATION_EXPIRATION_TIME = 5; // 결제 만료 시간 (분)
    private final String REDISSON_LOCK_KEY_PREFIX = "payment:";

    private final RedissonClient redissonClient;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 결제 좌석 등록
     */
    public void seatLock(
            final Long roundId,
            final Set<Long> newSeatIds,
            final Long userId
    ) {
        if (checkIfSeatsAlreadyReserved(roundId, newSeatIds, userId)) {
            throw new SeatGradeAlreadySoldOutException(SeatGradeErrorCode.SEAT_GRADE_ALREADY_SOLD_OUT);
        }

        if (!lockSeats(roundId, newSeatIds)) {
            throw new SeatGradeAlreadySoldOutException(SeatGradeErrorCode.SEAT_GRADE_ALREADY_SOLD_OUT);
        }

        try {
            updateSeatReservations(roundId, newSeatIds, userId);
        } finally {
            // 잠금 해제
            unlockSeats(roundId, newSeatIds);
        }
    }

    private boolean lockSeats(Long roundId, Set<Long> seatIds) {
        boolean allLocked = true;
        for (Long seatId : seatIds) {
            RLock lock = redissonClient.getLock(generateLockKeyForRoundAndSeat(roundId, seatId));
            try {
                if (!lock.tryLock(0, LOCK_EXPIRATION_TIME, TimeUnit.SECONDS)) {
                    unlockSeats(roundId, seatIds);
                    allLocked = false;
                    break;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                unlockSeats(roundId, seatIds);
                throw new SeatGradeAlreadySoldOutException(SeatGradeErrorCode.SEAT_GRADE_ALREADY_SOLD_OUT);
            }
        }
        return allLocked;
    }

    private void unlockSeats(Long roundId, Set<Long> seatIds) {
        for (Long seatId : seatIds) {
            RLock lock = redissonClient.getLock(generateLockKeyForRoundAndSeat(roundId, seatId));
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    /**
     * 새로운 좌석 결제 등록
     */
    private void updateSeatReservations(Long roundId, Set<Long> newSeatIds, Long userId) {
        for (Long seatId : newSeatIds) {
            String key = generateLockKeyForRoundSeatAndUser(roundId, seatId, userId);
            RBucket<String> bucket = redissonClient.getBucket(key);

            String existingReservation = bucket.get();

            if (existingReservation != null && !existingReservation.equals(userId.toString())) {
                log.error("좌석 {} 은(는) 다른 사용자에 의해 이미 예약되었습니다.", seatId);
                throw new SeatGradeAlreadySoldOutException(SeatGradeErrorCode.SEAT_GRADE_ALREADY_SOLD_OUT);
            }

            // 예매 좌석 업데이트
            bucket.setAsync(userId.toString(), RESERVATION_EXPIRATION_TIME, TimeUnit.MINUTES);
        }
    }

    /**
     * 이미 예매 된 좌석여부를 검증
     */
    private boolean checkIfSeatsAlreadyReserved(Long roundId, Set<Long> seatIds, Long userId) {
        for (Long seatId : seatIds) {
            String pattern = generateLockKeyForRoundAndSeat(roundId, seatId) + ":*";
            Set<String> keys = redisTemplate.keys(pattern);

            for (String key : keys) {
                String[] parts = key.split(":");
                if (parts.length == 4 && !parts[3].equals(userId.toString())) {
                    log.error("좌석 {} 은(는) 다른 사용자에 의해 이미 예약되었습니다.", seatId);
                    return true;
                }
            }
        }
        return false;
    }

    private String generateLockKeyForRoundAndSeat(Long roundId, Long seatId) {
        return REDISSON_LOCK_KEY_PREFIX + roundId + ":" + seatId;
    }

    private String generateLockKeyForRoundSeatAndUser(Long roundId, Long seatId, Long userId) {
        return REDISSON_LOCK_KEY_PREFIX + roundId + ":" + seatId + ":" + userId;
    }

    private String generateKeyPatternForRoundAndUser(Long roundId, Long userId) {
        return REDISSON_LOCK_KEY_PREFIX + roundId + ":*:" + userId;
    }

    public void releaseReservation(
            final Long roundId,
            final Long userId
    ) {
        String keyPattern = generateKeyPatternForRoundAndUser(roundId, userId);
        Iterable<String> keys = redissonClient.getKeys().getKeysByPattern(keyPattern);

        keys.forEach(k -> {
            redissonClient.getBucket(k).delete();
        });
    }
}
