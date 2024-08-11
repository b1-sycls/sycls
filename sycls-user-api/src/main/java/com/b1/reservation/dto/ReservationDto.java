package com.b1.reservation.dto;

import com.b1.seatgrade.entity.SeatGrade;
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
public class ReservationDto {

    private String seatGradeType;
    private Integer quantity;
    private Integer price;
    private Set<Long> seatGradeIds;
    private Set<String> seatCodes;

    public static ReservationDto of(
            final String type,
            final Integer quantity,
            final Integer price,
            final Set<Long> seatGradeIds,
            final Set<String> seatCodes
    ) {
        return ReservationDto.builder()
                .seatGradeType(type)
                .quantity(quantity)
                .price(price)
                .seatGradeIds(seatGradeIds)
                .seatCodes(seatCodes)
                .build();
    }

    public static ReservationDto of(String seatGradeType, int quantity, Integer price, List<SeatGrade> logs) {
        Map<Long, String> seatGradeMap = logs.stream()
                .collect(Collectors.toMap(
                        SeatGrade::getId,
                        srl -> srl.getSeat().getCode(),
                        (existing, replacement) -> existing // 중복 발생 시 기존 값을 사용
                ));

        return ReservationDto.builder()
                .seatGradeType(seatGradeType)
                .quantity(quantity)
                .price(price)
                .seatGradeIds(seatGradeMap.keySet())
                .seatCodes(new HashSet<>(seatGradeMap.values()))
                .build();
    }

}
