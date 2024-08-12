package com.b1.place.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PlaceGetEnableResponseDto {

    private Long placeId;
    private String location;
    private String name;
    
}
