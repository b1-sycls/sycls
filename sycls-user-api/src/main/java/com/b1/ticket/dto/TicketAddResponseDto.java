package com.b1.ticket.dto;

import com.b1.seat.entity.SeatGrade;
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
public class TicketAddResponseDto {

    private final Long roundId;
    private final Set<Long> seatGradeIds;
    private final Set<String> seatCodes;

    public static TicketAddResponseDto of(Long roundId, Set<SeatGrade> seatGrades) {
        Map<Long, String> seatGradeMap = seatGrades.stream()
                .collect(Collectors.toMap(SeatGrade::getId, sg -> sg.getSeat().getCode()));

        return TicketAddResponseDto.builder()
                .roundId(roundId)
                .seatGradeIds(seatGradeMap.keySet())
                .seatCodes(new HashSet<>(seatGradeMap.values()))
                .build();
    }
}
