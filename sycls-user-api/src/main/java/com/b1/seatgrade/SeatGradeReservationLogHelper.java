package com.b1.seatgrade;

import static com.b1.constant.ReservationConstant.SEAT_RESERVATION_TIME;

import com.b1.exception.customexception.SeatReservationLogNotAvailableException;
import com.b1.exception.customexception.SeatReservationLogNotFoundException;
import com.b1.exception.errorcode.SeatReservationLogErrorCode;
import com.b1.seatgrade.entity.SeatGrade;
import com.b1.seatgrade.entity.SeatGradeReservationLog;
import com.b1.seatgrade.entity.SeatGradeReservationLogStatus;
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

@Slf4j(topic = "Seat Reservation Log Helper")
@Component
@RequiredArgsConstructor
public class SeatGradeReservationLogHelper {

    private final SeatReservationLogRepository seatReservationLogRepository;

    /**
     * 좌석 등급의 예매 상태를 확인하고 필요 시 예매 생성
     *
     * @throws SeatReservationLogNotAvailableException 이미 매진 된 좌석 또는 점유 중인 좌석
     */
    public Set<SeatGradeReservationLog> getSeatReservationLogsBySeatGrade(
            final Set<SeatGrade> seatGrades
    ) {
        LocalDateTime currentTime = LocalDateTime.now();

        List<SeatGradeReservationLog> reservationLogs = seatReservationLogRepository
                .findAllBySeatGradeInAndCreatedAtAfterAndStatus(
                        seatGrades,
                        currentTime.minusMinutes(SEAT_RESERVATION_TIME),
                        SeatGradeReservationLogStatus.ENABLE);

        Map<Long, SeatGradeReservationLog> latestLogsBySeatGradeId = getLongSeatReservationLogMap(
                reservationLogs);

        return new HashSet<>(latestLogsBySeatGradeId.values());
    }

    /**
     * 특정 유저 예매 좌석 조회
     */
    public Set<SeatGradeReservationLog> getSeatReservationLogsByUser(
            final User user
    ) {
        LocalDateTime currentTime = LocalDateTime.now();

        List<SeatGradeReservationLog> reservationLogs = seatReservationLogRepository
                .findAllByUserAndCreatedAtAfterAndStatus(
                        user,
                        currentTime.minusMinutes(SEAT_RESERVATION_TIME),
                        SeatGradeReservationLogStatus.ENABLE);

        Map<Long, SeatGradeReservationLog> latestLogsBySeatGradeId = getLongSeatReservationLogMap(
                reservationLogs);

        return new HashSet<>(latestLogsBySeatGradeId.values());
    }

    /**
     * 특정 유저 예매 좌석 조회
     */
    public Map<String, List<SeatGradeReservationLog>> getSeatReservationLogsBySeatGrade(
            final User user
    ) {
        LocalDateTime currentTime = LocalDateTime.now();

        List<SeatGradeReservationLog> reservationLogs = seatReservationLogRepository
                .findAllByUserAndCreatedAtAfterAndStatus(
                        user,
                        currentTime.minusMinutes(SEAT_RESERVATION_TIME),
                        SeatGradeReservationLogStatus.ENABLE);

        return getLogSeatGradeMap(reservationLogs);
    }

    /**
     * 중복 예매에 대한 검증
     */
    public boolean isProcessReservation(
            final Set<SeatGradeReservationLog> allSeatGradeReservationLogs,
            final User user,
            final Set<Long> seatIds
    ) {
        LocalDateTime currentTime = LocalDateTime.now();

        Map<Long, Set<SeatGradeReservationLog>> logsBySeatId = allSeatGradeReservationLogs.stream()
                .collect(Collectors.groupingBy(srl -> srl.getSeatGrade().getId(),
                        Collectors.toSet()));

        boolean newReservationRequired = false;

        for (Long seatId : seatIds) {
            Set<SeatGradeReservationLog> seatLogs = logsBySeatId.getOrDefault(seatId, Set.of());

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
                        SeatReservationLogErrorCode.SEAT_RESERVATION_ALREADY_DISABLE);
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
    public Set<SeatGradeReservationLog> getSeatReservationLogByUser(
            final Set<Long> reservationIds,
            final User user
    ) {
        Set<SeatGradeReservationLog> seatGradeReservationLogs = seatReservationLogRepository
                .findAllByIdInAndUser(reservationIds, user);
        if ((seatGradeReservationLogs.isEmpty())) {
            log.error("찾을 수 없는 예매 좌석 정보 입니다. | request {}", reservationIds);
            throw new SeatReservationLogNotFoundException(
                    SeatReservationLogErrorCode.SEAT_RESERVATION_NOT_FOUND);
        }
        SeatGradeReservationLogStatus.checkDisables(seatGradeReservationLogs);
        return seatGradeReservationLogs;
    }

    /**
     * 예매 등록
     */
    public void addAllSeatReservationLogs(
            final Set<SeatGradeReservationLog> createSeatGradeReservationLogs
    ) {
        seatReservationLogRepository.saveAll(createSeatGradeReservationLogs);
    }

    /**
     * 중복 제거 및 최신 데이터 추출
     */
    private Map<Long, SeatGradeReservationLog> getLongSeatReservationLogMap(
            final List<SeatGradeReservationLog> reservationLogs
    ) {
        return reservationLogs.stream()
                .collect(Collectors.toMap(
                        log -> log.getSeatGrade().getId(),
                        log -> log,
                        (existingLog, newLog) ->
                                existingLog.getCreatedAt().isAfter(newLog.getCreatedAt())
                                        ? existingLog : newLog));
    }

    /**
     * 좌석 등급별 데이터 추출
     */
    private Map<String, List<SeatGradeReservationLog>> getLogSeatGradeMap(
            final List<SeatGradeReservationLog> reservationLogs
    ) {
        return reservationLogs.stream()
                .collect(Collectors.groupingBy(
                        log -> log.getSeatGrade().getGrade().getValue()
                ));
    }

    /**
     * 특정 예매 정보 조회
     */
    public List<SeatGradeReservationLog> getSeatReservationLogsById(
            final List<Long> reservationIds
    ) {
        return seatReservationLogRepository.findAllByIdIn(reservationIds);
    }
}
