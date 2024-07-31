package com.b1.reservation.dto;

import com.b1.round.entity.Round;
import com.b1.seatgrade.entity.SeatGradeReservationLog;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SeatGradeReservationDto {

    private String seatGradeType;
    private Integer quantity;
    private Integer price;
    private Set<Long> reservationIds;
    private Set<Long> seatGradeIds;
    private Set<String> seatCodes;

    public static SeatGradeReservationDto of(
            final String type,
            final Integer quantity,
            final Integer price,
            final Set<Long> reservationIds,
            final Set<Long> seatGradeIds,
            final Set<String> seatCodes
    ) {
        return SeatGradeReservationDto.builder()
                .seatGradeType(type)
                .quantity(quantity)
                .price(price)
                .reservationIds(reservationIds)
                .seatGradeIds(seatGradeIds)
                .seatCodes(seatCodes)
                .build();
    }

    public static SeatGradeReservationDto of(String seatGradeType, int quantity, Integer price, List<SeatGradeReservationLog> logs) {
        Set<Long> reservationIds = logs.stream()
                .map(SeatGradeReservationLog::getId)
                .collect(Collectors.toSet());

        Map<Long, String> seatGradeMap = logs.stream()
                .collect(Collectors.toMap(
                        srl -> srl.getSeatGrade().getId(),
                        srl -> srl.getSeatGrade().getSeat().getCode(),
                        (existing, replacement) -> existing // 중복 발생 시 기존 값을 사용
                ));

        return SeatGradeReservationDto.builder()
                .seatGradeType(seatGradeType)
                .quantity(quantity)
                .price(price)
                .reservationIds(reservationIds)
                .seatGradeIds(seatGradeMap.keySet())
                .seatCodes(new HashSet<>(seatGradeMap.values()))
                .build();
    }

}
