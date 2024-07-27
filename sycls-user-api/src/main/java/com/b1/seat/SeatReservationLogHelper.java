package com.b1.seat;

import static com.b1.constant.DomainConstant.SEAT_RESERVATION_TIME;

import com.b1.exception.customexception.SeatReservationLogNotAvailableException;
import com.b1.exception.errorcode.SeatReservationLogErrorCode;
import com.b1.seat.entity.SeatReservationLog;
import com.b1.seat.entity.SeatReservationLogStatus;
import com.b1.seatgrade.entity.SeatGrade;
import com.b1.user.entity.User;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "Seat Reservation Log Helper")
@Component
@RequiredArgsConstructor
public class SeatReservationLogHelper {

    private final SeatReservationLogRepository seatReservationLogRepository;

    /**
     * 좌석 등급의 예매 상태를 확인하고 필요 시 예매 생성
     *
     * @throws SeatReservationLogNotAvailableException 이미 매진 된 좌석 또는 점유 중인 좌석
     */
    public Set<SeatReservationLog> getSeatReservationLogsBySeatGrade(
            final Set<SeatGrade> seatGrades,
            User user) {
        LocalDateTime currentTime = LocalDateTime.now();

        List<SeatReservationLog> reservationLogs = seatReservationLogRepository
                .findAllBySeatGradeInAndCreatedAtAfterAndStatus(
                        seatGrades,
                        currentTime.minusMinutes(SEAT_RESERVATION_TIME),
                        SeatReservationLogStatus.ENABLE);

        Map<Long, SeatReservationLog> latestLogsBySeatGradeId = getLongSeatReservationLogMap(
                reservationLogs);

        return new HashSet<>(latestLogsBySeatGradeId.values());
    }

    /**
     * 좌석 등급의 예매 상태를 확인하고 필요 시 예매 생성
     *
     * @throws SeatReservationLogNotAvailableException 이미 매진 된 좌석 또는 점유 중인 좌석
     */
    public Set<SeatReservationLog> getSeatReservationLogsByUser(
            final User user
    ) {
        LocalDateTime currentTime = LocalDateTime.now();

        List<SeatReservationLog> reservationLogs = seatReservationLogRepository
                .findAllByUserAndCreatedAtAfterAndStatus(
                        user,
                        currentTime.minusMinutes(SEAT_RESERVATION_TIME),
                        SeatReservationLogStatus.ENABLE);

        Map<Long, SeatReservationLog> latestLogsBySeatGradeId = getLongSeatReservationLogMap(
                reservationLogs);

        return new HashSet<>(latestLogsBySeatGradeId.values());
    }

    /**
     * 중복 예매에 대한 검증
     */
    public boolean isProcessReservation(
            final Set<SeatReservationLog> allSeatReservationLogs,
            final User user,
            final Set<Long> seatIds
    ) {
        LocalDateTime currentTime = LocalDateTime.now();

        Map<Long, Set<SeatReservationLog>> logsBySeatId = allSeatReservationLogs.stream()
                .collect(Collectors.groupingBy(srl -> srl.getSeatGrade().getId(),
                        Collectors.toSet()));

        boolean newReservationRequired = false;

        for (Long seatId : seatIds) {
            Set<SeatReservationLog> seatLogs = logsBySeatId.getOrDefault(seatId, Set.of());

            boolean isReservedByUser = seatLogs.stream()
                    .anyMatch(srl -> srl.getCreatedAt().plusMinutes(SEAT_RESERVATION_TIME)
                            .isAfter(currentTime)
                            && srl.getUser().getId().equals(user.getId()));

            if (seatLogs.stream().anyMatch(
                    srl -> srl.getCreatedAt().plusMinutes(SEAT_RESERVATION_TIME)
                            .isAfter(currentTime)
                            && !srl.getUser().getId().equals(user.getId()))) {
                log.error("점유 중 좌석 등급 | request {}", user.getId());
                throw new SeatReservationLogNotAvailableException(
                        SeatReservationLogErrorCode.SEAT_RESERVATION_NOT_AVAILABLE);
            }

            if (!isReservedByUser) {
                newReservationRequired = true;
            }
        }

        return newReservationRequired;
    }

    /**
     * 예매 정보 조회
     */
    public Set<SeatReservationLog> getSeatReservationLogByUser(
            final Set<Long> reservationIds,
            final User user
    ) {
        return seatReservationLogRepository.findAllByIdInAndUser(reservationIds, user);
    }

    /**
     * 예매 등록
     */
    public void addAllSeatReservationLogs(
            final Set<SeatReservationLog> createSeatReservationLogs
    ) {
        seatReservationLogRepository.saveAll(createSeatReservationLogs);
    }

    /**
     * 예매 취소
     */
    public void deleteReservationLog(Set<SeatReservationLog> seatReservationLogByUser) {
        for (SeatReservationLog seatReservationLog : seatReservationLogByUser) {
            seatReservationLog.deleteReservationStatus();
        }
    }

    private Map<Long, SeatReservationLog> getLongSeatReservationLogMap(
            List<SeatReservationLog> reservationLogs) {
        return reservationLogs.stream()
                .collect(Collectors.toMap(
                        log -> log.getSeatGrade().getId(),
                        log -> log,
                        (existingLog, newLog) ->
                                existingLog.getCreatedAt().isAfter(newLog.getCreatedAt())
                                        ? existingLog : newLog));
    }

}
