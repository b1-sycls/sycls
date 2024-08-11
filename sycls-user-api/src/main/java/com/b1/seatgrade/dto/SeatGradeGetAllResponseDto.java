package com.b1.seatgrade.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class SeatGradeGetAllResponseDto {

    private Long roundId;
    private Integer totalCount;
    private List<SeatGradeGetResponseDto> seatGradeList;

    public static SeatGradeGetAllResponseDto of(
            final Long roundId,
            final List<SeatGradeGetResponseDto> seatGradeList
    ) {
        return SeatGradeGetAllResponseDto.builder()
                .roundId(roundId)
                .totalCount(seatGradeList.size())
                .seatGradeList(seatGradeList)
                .build();
    }

}