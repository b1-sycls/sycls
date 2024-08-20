package com.b1.reservation;

import com.b1.constant.RedissonConstants;
import com.b1.redis.RedissonRepository;
import com.b1.seatgrade.SeatGradeRepository;
import com.b1.seatgrade.entity.SeatGrade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.b1.constant.RedissonConstants.*;
import static com.b1.constant.RedissonConstants.REDISSON_RESERVATION_LOCK_KEY_PREFIX;

@Slf4j(topic = "Reservation Helper")
@Component
@RequiredArgsConstructor
public class ReservationHelper {

    private final SeatGradeRepository seatGradeRepository;
    private final RedissonRepository redissonRepository;

    /**
     * 선택 좌석 점유 등록
     */
    public void addReservation(
            final Long roundId,
            final Set<Long> seatGradeIds,
            final Long userId
    ) {
        redissonRepository.reserveSeats(REDISSON_RESERVATION_LOCK_KEY_PREFIX, roundId, seatGradeIds, userId);
    }

    /**
     * 특정 유저 예매 좌석 조회
     */
    public Set<Long> getReservationByUser(
            final Long roundId,
            final Long userId
    ) {
        return redissonRepository
                .getReservationByUser(REDISSON_RESERVATION_LOCK_KEY_PREFIX, roundId, userId);
    }

    /**
     * 예매 좌석 취소
     */
    public void releaseReservation(
            final Long roundId,
            final Long userId
    ) {
        redissonRepository.releaseReservation(REDISSON_RESERVATION_LOCK_KEY_PREFIX, roundId, userId);

    }

    /**
     * 특정 유저 예매 좌석 상세 조회
     */
    public Map<String, List<SeatGrade>> getReservationDetailByUser(
            final Long roundId,
            final Long userId
    ) {
        Set<Long> seatReservationIds = redissonRepository
                .getReservationByUser(REDISSON_RESERVATION_LOCK_KEY_PREFIX, roundId, userId);
        List<SeatGrade> selectSeatGrades = seatGradeRepository.findAllByIdIn(seatReservationIds);

        return getLogSeatGradeMap(selectSeatGrades);
    }

    /**
     * 점유 중인 좌석 조회
     */
    public Set<Long> getOccupied(
            final Long roundId
    ) {
        return redissonRepository.getOccupied(REDISSON_RESERVATION_LOCK_KEY_PREFIX, roundId);
    }

    /**
     * 예매 중인 좌석 조회
     */
    public List<SeatGrade> getReservationByUserWithPayment(
            final Long roundId,
            final Long userId
    ) {
        Set<Long> seatReservationIds = redissonRepository
                .getReservationByUser(REDISSON_RESERVATION_LOCK_KEY_PREFIX, roundId, userId);
        return seatGradeRepository.findAllByIdIn(seatReservationIds);
    }

    /**
     * 좌석 등급별 데이터 추출
     */
    private Map<String, List<SeatGrade>> getLogSeatGradeMap(
            final List<SeatGrade> seatGrades
    ) {
        return seatGrades.stream()
                .collect(Collectors.groupingBy(
                        sg -> sg.getGrade().getValue()
                ));
    }

    /**
     * 결제 완료 후 점유 기록 삭제
     */
    public void clearReservation(
            final Long roundId,
            final Long userId
    ) {
        redissonRepository.releaseReservation(REDISSON_RESERVATION_LOCK_KEY_PREFIX, roundId, userId);
    }
}
