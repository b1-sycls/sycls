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

import static com.b1.constant.PaymentConstants.LOCK_EXPIRATION_TIME;
import static com.b1.constant.PaymentConstants.REDISSON_LOCK_KEY_PREFIX;
import static com.b1.constant.PaymentConstants.RESERVATION_EXPIRATION_TIME;

@Slf4j(topic = "Payment Repository")
@Repository
@RequiredArgsConstructor
public class PaymentRepository {

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
            log.error("이미 매진된 좌석 {}", newSeatIds);
            throw new SeatGradeAlreadySoldOutException(SeatGradeErrorCode.SEAT_GRADE_ALREADY_SOLD_OUT);
        }

        if (!lockSeats(roundId, newSeatIds)) {
            log.error("이미 매진된 좌석 {}", newSeatIds);
            throw new SeatGradeAlreadySoldOutException(SeatGradeErrorCode.SEAT_GRADE_ALREADY_SOLD_OUT);
        }

        try {
            updateSeatReservations(roundId, newSeatIds, userId);
        } finally {
            // 잠금 해제
            unlockSeats(roundId, newSeatIds);
        }
    }

    /**
     * 기존 예매 기록 삭제
     */
    public void releaseReservation(
            final Long roundId,
            final Long userId
    ) {
        String keyPattern = generateKeyPatternForRoundAndUser(roundId, userId);
        Iterable<String> keys = redissonClient.getKeys().getKeysByPattern(keyPattern);

        keys.forEach(k -> redissonClient.getBucket(k).delete());
    }

    /**
     * 좌석 lock
     */
    private boolean lockSeats(
            final Long roundId,
            final Set<Long> seatIds
    ) {
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
                log.error("이미 매진된 좌석 {} {}", seatId, seatIds);
                throw new SeatGradeAlreadySoldOutException(SeatGradeErrorCode.SEAT_GRADE_ALREADY_SOLD_OUT);
            }
        }
        return allLocked;
    }

    /**
     * 기존 lock 해제
     */
    private void unlockSeats(
            final Long roundId,
            final Set<Long> seatIds
    ) {
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
    private void updateSeatReservations(
            final Long roundId,
            final Set<Long> newSeatIds,
            final Long userId
    ) {
        for (Long seatId : newSeatIds) {
            String key = generateLockKeyForRoundSeatAndUser(roundId, seatId, userId);
            RBucket<String> bucket = redissonClient.getBucket(key);

            String existingReservation = bucket.get();

            if (existingReservation != null && !existingReservation.equals(userId.toString())) {
                log.error("좌석 {} 은(는) 다른 사용자에 의해 이미 예약", seatId);
                throw new SeatGradeAlreadySoldOutException(SeatGradeErrorCode.SEAT_GRADE_ALREADY_SOLD_OUT);
            }

            // 예매 좌석 업데이트
            bucket.setAsync(userId.toString(), RESERVATION_EXPIRATION_TIME, TimeUnit.MINUTES);
        }
    }

    /**
     * 이미 예매 된 좌석여부를 검증
     */
    private boolean checkIfSeatsAlreadyReserved(
            final Long roundId,
            final Set<Long> seatIds,
            final Long userId
    ) {
        for (Long seatId : seatIds) {
            String pattern = generateLockKeyForRoundAndSeat(roundId, seatId) + ":*";
            Set<String> keys = redisTemplate.keys(pattern);

            for (String key : keys) {
                String[] parts = key.split(":");
                if (parts.length == 4 && !parts[3].equals(userId.toString())) {
                    log.error("좌석 {} 은(는) 다른 사용자에 의해 이미 예약", seatId);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * payment:{roundId}:{seatId}
     */
    private String generateLockKeyForRoundAndSeat(
            final Long roundId,
            final Long seatId
    ) {
        return REDISSON_LOCK_KEY_PREFIX + roundId + ":" + seatId;
    }

    /**
     * payment:{roundId}:{seatId}:{userId}
     */
    private String generateLockKeyForRoundSeatAndUser(
            final Long roundId,
            final Long seatId,
            final Long userId
    ) {
        return REDISSON_LOCK_KEY_PREFIX + roundId + ":" + seatId + ":" + userId;
    }

    /**
     * payment:{roundId}:*{userId}
     */
    private String generateKeyPatternForRoundAndUser(
            final Long roundId,
            final Long userId
    ) {
        return REDISSON_LOCK_KEY_PREFIX + roundId + ":*:" + userId;
    }

}
