package com.b1.seatgrade.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SeatGradeGetAllResponseDto {

    private Long roundId;
    private Integer totalCount;
    private List<SeatGradeUserGetResponseDto> seatGradeList;

    public static SeatGradeGetAllResponseDto of(
            Long roundId,
            List<SeatGradeUserGetResponseDto> seatGradeList
    ) {
        return SeatGradeGetAllResponseDto.builder()
                .roundId(roundId)
                .totalCount(seatGradeList.size())
                .seatGradeList(seatGradeList)
                .build();
    }

}