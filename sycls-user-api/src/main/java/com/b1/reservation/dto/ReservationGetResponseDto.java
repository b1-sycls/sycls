package com.b1.reservation.dto;

import com.b1.round.entity.Round;
import com.b1.seatgrade.entity.SeatGrade;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationGetResponseDto {

    private final Long roundId;
    private final Set<Long> seatGradeIds;
    private final Set<String> seatCodes;

    public static ReservationGetResponseDto of(
            final Round selectedRound,
            final Set<SeatGrade> seatGrades) {

        Map<Long, String> seatGradeMap = seatGrades.stream()
                .collect(Collectors.toMap(
                        SeatGrade::getId,
                        srl -> srl.getSeat().getCode()));

        return ReservationGetResponseDto.builder()
                .roundId(selectedRound.getId())
                .seatGradeIds(seatGradeMap.keySet())
                .seatCodes(new HashSet<>(seatGradeMap.values()))
                .build();
    }
}