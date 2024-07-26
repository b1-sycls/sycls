package com.b1.seatgrade.dto;

import com.b1.seatgrade.entity.SeatGradeStatus;
import com.b1.seatgrade.entity.SeatGradeType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SeatGradeAdminGetResponseDto {

    private Long seatId;
    private String seatCode;
    private Long seatGradeId;
    private SeatGradeType seatGradeType;
    private Integer seatGradePrice;
    private SeatGradeStatus seatGradeStatus;

}
