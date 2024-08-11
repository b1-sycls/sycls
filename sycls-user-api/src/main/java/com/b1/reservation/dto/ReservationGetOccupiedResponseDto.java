package com.b1.reservation.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationGetOccupiedResponseDto {

    private final Long roundId;
    private final Set<Long> seatGradeIds;

    public static ReservationGetOccupiedResponseDto of(
            final Long roundId,
            final Set<Long> seatOccupiedIds
    ) {
        return ReservationGetOccupiedResponseDto.builder()
                .roundId(roundId)
                .seatGradeIds(seatOccupiedIds)
                .build();
    }
}
