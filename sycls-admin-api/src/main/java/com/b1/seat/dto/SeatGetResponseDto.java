package com.b1.seat.dto;

import com.b1.seat.entity.Seat;
import com.b1.seat.entity.SeatStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class SeatGetResponseDto {

    private Long seatId;
    private String code;
    private SeatStatus status;

    public static SeatGetResponseDto of(Seat seat) {
        return SeatGetResponseDto.builder()
                .seatId(seat.getId())
                .code(seat.getCode())
                .status(seat.getStatus())
                .build();
    }
}
