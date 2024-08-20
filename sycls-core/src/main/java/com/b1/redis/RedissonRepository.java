package com.b1.redis;

import com.b1.exception.customexception.SeatGradeAlreadySoldOutException;
import com.b1.exception.errorcode.SeatGradeErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.b1.constant.RedissonConstants.LOCK_EXPIRATION_TIME;
import static com.b1.constant.RedissonConstants.RESERVATION_EXPIRATION_TIME;

@Slf4j(topic = "Redisson Repository")
@Repository
@RequiredArgsConstructor
public class RedissonRepository {

    private final RedissonClient redissonClient;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 예매 등록
     */
    public void reserveSeats(
            final String prefix,
            final Long roundId,
            final Set<Long> newSeatIds,
            final Long userId
    ) {
        validateSeatsAvailability(prefix, roundId, newSeatIds, userId);

        lockSeatsOrThrow(prefix, roundId, newSeatIds);

        try {
            removeExistingReservations(prefix, roundId, userId);

            updateSeatReservations(prefix, roundId, newSeatIds, userId);
        } finally {
            // 잠금 해제
            unlockSeats(prefix, roundId, newSeatIds);
        }
    }

    /**
     * 결제 좌석 등록
     */
    public void seatLock(
            final String prefix,
            final Long roundId,
            final Set<Long> newSeatIds,
            final Long userId
    ) {
        validateSeatsAvailability(prefix, roundId, newSeatIds, userId);

        lockSeatsOrThrow(prefix, roundId, newSeatIds);

        try {
            updateSeatReservations(prefix, roundId, newSeatIds, userId);
        } finally {
            // 잠금 해제
            unlockSeats(prefix, roundId, newSeatIds);
        }
    }

    /**
     * 예매 정보 조회
     */
    public Set<Long> getReservationByUser(
            final String prefix,
            final Long roundId,
            final Long userId
    ) {
        String keyPattern = generateKeyPatternForRoundAndUser(prefix, roundId, userId);
        Set<String> keys = redisTemplate.keys(keyPattern);
        return keys.stream().map(k -> Long.parseLong(k.split(":")[2])
        ).collect(Collectors.toSet());
    }

    /**
     * 예매 중인 좌석 취소
     */
    public void releaseReservation(
            final String prefix,
            final Long roundId,
            final Long userId
    ) {
        String keyPattern = generateKeyPatternForRoundAndUser(prefix, roundId, userId);
        Iterable<String> keys = redissonClient.getKeys().getKeysByPattern(keyPattern);

        keys.forEach(k -> redissonClient.getBucket(k).delete());
    }

    /**
     * 점유 중인 좌석 조회
     */
    public Set<Long> getOccupied(
            final String prefix,
            final Long roundId
    ) {
        String keyPattern = generateKeyPatternForRound(prefix, roundId);
        Set<String> keys = redisTemplate.keys(keyPattern);
        return keys.stream().map(k -> Long.parseLong(k.split(":")[2])
        ).collect(Collectors.toSet());
    }

    /**
     * 좌석 잠금 시도 및 잠금 여부 검증
     */
    private boolean lockSeats(
            final String prefix,
            final Long roundId,
            final Set<Long> seatIds
    ) {
        boolean allLocked = true;
        for (Long seatId : seatIds) {
            RLock lock = redissonClient.getLock(generateLockKeyForRoundAndSeat(prefix, roundId, seatId));
            try {
                if (!lock.tryLock(0, LOCK_EXPIRATION_TIME, TimeUnit.SECONDS)) {
                    unlockSeats(prefix, roundId, seatIds);
                    allLocked = false;
                    break;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                unlockSeats(prefix, roundId, seatIds);
                log.error("이미 매진된 좌석 {}", seatIds);
                throw new SeatGradeAlreadySoldOutException(SeatGradeErrorCode.SEAT_GRADE_ALREADY_SOLD_OUT);
            }
        }
        return allLocked;
    }

    /**
     * 좌석 잠금 시도 매진 된 경우 예외 발생
     */
    private void lockSeatsOrThrow(String prefix, Long roundId, Set<Long> newSeatIds) {
        if (!lockSeats(prefix, roundId, newSeatIds)) {
            log.error("이미 매진된 좌석 {}", newSeatIds);
            throw new SeatGradeAlreadySoldOutException(SeatGradeErrorCode.SEAT_GRADE_ALREADY_SOLD_OUT);
        }
    }

    /**
     * 매진 된 좌석 여부 검증
     */
    private void validateSeatsAvailability(String prefix, Long roundId, Set<Long> newSeatIds, Long userId) {
        if (checkIfSeatsAlreadyReserved(prefix, roundId, newSeatIds, userId)) {
            log.error("이미 매진된 좌석 {}", newSeatIds);
            throw new SeatGradeAlreadySoldOutException(SeatGradeErrorCode.SEAT_GRADE_ALREADY_SOLD_OUT);
        }
    }

    /**
     * 기존 lock 해제
     */
    private void unlockSeats(
            final String prefix,
            final Long roundId,
            final Set<Long> seatIds
    ) {
        for (Long seatId : seatIds) {
            RLock lock = redissonClient.getLock(generateLockKeyForRoundAndSeat(prefix, roundId, seatId));
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    /**
     * 기존 예매 좌석 삭제
     */
    private void removeExistingReservations(
            final String prefix,
            final Long roundId,
            final Long userId
    ) {
        RKeys keys = redissonClient.getKeys();
        String pattern = generateKeyPatternForRoundAndUser(prefix, roundId, userId);
        keys.getKeysByPattern(pattern).forEach(key -> {
            RBucket<String> bucket = redissonClient.getBucket(key);
            if (bucket.get() != null) {
                bucket.deleteAsync().whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("기존 예약된 좌석 {} 의 예약 삭제 실패", key, ex);
                    } else {
                        log.info("기존 예약된 좌석 {} 의 예약을 삭제", key);
                    }
                });
            }
        });
    }

    /**
     * 새로운 좌석 예매 등록
     */
    private void updateSeatReservations(
            final String prefix,
            final Long roundId,
            final Set<Long> newSeatIds,
            final Long userId
    ) {
        for (Long seatId : newSeatIds) {
            String key = generateLockKeyForRoundSeatAndUser(prefix, roundId, seatId, userId);
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
            final String prefix,
            final Long roundId,
            final Set<Long> seatIds,
            final Long userId
    ) {
        for (Long seatId : seatIds) {
            String pattern = generateLockKeyForRoundAndSeat(prefix, roundId, seatId) + ":*";
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

    /**
     * reservation:{roundId}:*:*
     */
    private String generateKeyPatternForRound(final String prefix, final Long roundId) {
        return prefix + roundId + ":*:*";
    }

    /**
     * reservation:{roundId}:{seatId}
     */
    private String generateLockKeyForRoundAndSeat(
            final String prefix,
            final Long roundId,
            final Long seatId
    ) {
        return prefix + roundId + ":" + seatId;
    }

    /**
     * reservation:{roundId}:{seatId}:{userId}
     */
    private String generateLockKeyForRoundSeatAndUser(
            final String prefix,
            final Long roundId,
            final Long seatId,
            final Long userId
    ) {
        return prefix + roundId + ":" + seatId + ":" + userId;
    }

    /**
     * reservation:{roundId}:*:{userId}
     */
    private String generateKeyPatternForRoundAndUser(
            final String prefix,
            final Long roundId,
            final Long userId
    ) {
        return prefix + roundId + ":*:" + userId;
    }
}
