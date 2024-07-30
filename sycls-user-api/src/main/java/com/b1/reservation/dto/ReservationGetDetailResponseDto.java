package com.b1.reservation.dto;

import com.b1.seatgrade.entity.SeatGradeReservationLog;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationGetDetailResponseDto {

    private Long roundId;
    private List<SeatGradeReservationDto> seatInfos;

    public static ReservationGetDetailResponseDto of(
            final Long roundId,
            final Map<String, List<SeatGradeReservationLog>> seatInfos
    ) {
        List<SeatGradeReservationDto> convertedSeatInfos = seatInfos.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .map(log -> SeatGradeReservationDto.of(
                                log.getSeatGrade().getGrade().getValue(),
                                entry.getKey().length(),
                                log.getSeatGrade().getPrice(),
                                entry.getValue()
                        ))
                )
                .collect(Collectors.toList());

        // Build and return the ReservationGetDetailResponseDto instance
        return ReservationGetDetailResponseDto.builder()
                .roundId(roundId)
                .seatInfos(convertedSeatInfos)
                .build();
    }
}