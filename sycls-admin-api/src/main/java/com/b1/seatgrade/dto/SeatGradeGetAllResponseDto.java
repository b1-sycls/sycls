package com.b1.seatgrade.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SeatGradeGetAllResponseDto {

    private Long roundId;
    private Integer totalCount;
    private List<SeatGradeAdminGetResponseDto> seatGradeList;

    public static SeatGradeGetAllResponseDto of(
            final Long roundId,
            final List<SeatGradeAdminGetResponseDto> seatGradeList
    ) {
        return SeatGradeGetAllResponseDto.builder()
                .roundId(roundId)
                .totalCount(seatGradeList.size())
                .seatGradeList(seatGradeList)
                .build();
    }

}
