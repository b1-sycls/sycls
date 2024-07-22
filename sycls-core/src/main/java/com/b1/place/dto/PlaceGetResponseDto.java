package com.b1.place.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PlaceGetResponseDto {

    private Long placeId;
    private String location;
    private String name;
    private Integer maxSeat;
    
}
