package com.b1.reservation.dto;

import com.b1.seatgrade.entity.SeatGradeReservationLog;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationGetDetailResponseDto {

    private List<SeatGradeReservationDto> seatInfos;

    public static ReservationGetDetailResponseDto of(
            final Map<String, List<SeatGradeReservationLog>> seatInfos
    ) {
        Map<String, List<SeatGradeReservationLog>> mergedSeatInfos = new HashMap<>();

        // 좌석 등급별로 데이터를 합침
        seatInfos.forEach((gradeType, logs) -> {
            mergedSeatInfos.merge(gradeType, logs, (existingLogs, newLogs) -> {
                existingLogs.addAll(newLogs);
                return existingLogs;
            });
        });

        // SeatGradeReservationDto 리스트로 변환
        List<SeatGradeReservationDto> convertedSeatInfos = mergedSeatInfos.entrySet().stream()
                .map(entry -> {
                    List<SeatGradeReservationLog> logs = entry.getValue();
                    int quantity = logs.size();
                    int price = logs.get(0).getSeatGrade().getPrice(); // 모든 로그의 가격이 동일하다고 가정
                    return SeatGradeReservationDto.of(entry.getKey(), quantity, price, logs);
                })
                .collect(Collectors.toList());

        return ReservationGetDetailResponseDto.builder()
                .seatInfos(convertedSeatInfos)
                .build();
    }
}
