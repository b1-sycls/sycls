package com.b1.round.dto;

import com.b1.place.entity.PlaceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoundCheckPlaceResponseDto {

    private Long contentId;
    private Long roundId;
    private Long placeId;
    private PlaceStatus placeStatus;

}
