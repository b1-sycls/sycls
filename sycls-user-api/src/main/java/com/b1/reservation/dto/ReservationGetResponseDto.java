package com.b1.reservation.dto;

import com.b1.round.entity.Round;
import com.b1.seat.entity.SeatReservationLog;
import com.b1.seatgrade.entity.SeatGrade;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationGetResponseDto {

    private final Long roundId;
    private final Set<Long> seatGradeIds;
    private final Set<String> seatCodes;

    public static ReservationGetResponseDto of(
            final Round selectedRound,
            final Set<SeatReservationLog> findSeatReservationLogs) {
        Map<Long, String> seatGradeMap = findSeatReservationLogs.stream()
                .collect(Collectors.toMap(srl -> srl.getSeatGrade().getId(),
                        srl -> srl.getSeatGrade().getSeat().getCode()));

        return ReservationGetResponseDto.builder()
                .roundId(selectedRound.getId())
                .seatGradeIds(seatGradeMap.keySet())
                .seatCodes(new HashSet<>(seatGradeMap.values()))
                .build();
    }
}
