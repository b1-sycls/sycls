package com.b1.seatgrade.dto;

import com.b1.seatgrade.entity.SeatGradeStatus;
import com.b1.seatgrade.entity.SeatGradeType;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class SeatGradeGetResponseDto {

    private Long seatId;
    private String seatCode;
    private Long seatGradeId;
    private SeatGradeType seatGradeType;
    private Integer seatGradePrice;
    private Boolean seatStatusYn;

    public static List<SeatGradeGetResponseDto> of(final List<SeatGradeUserGetDto> dtoList) {

        return dtoList.stream().map(
                dto -> SeatGradeGetResponseDto.builder()
                        .seatId(dto.getSeatId())
                        .seatCode(dto.getSeatCode())
                        .seatGradeId(dto.getSeatGradeId())
                        .seatGradeType(dto.getSeatGradeType())
                        .seatGradePrice(dto.getSeatGradePrice())
                        .seatStatusYn(SeatGradeStatus.isEnableStatus(dto.getSeatStatus()))
                        .build()
        ).toList();
    }

}
