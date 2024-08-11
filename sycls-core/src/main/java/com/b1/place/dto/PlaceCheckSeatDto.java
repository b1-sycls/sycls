package com.b1.place.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceCheckSeatDto {

    private Integer maxSeat;
    private Long seatCount;

}
