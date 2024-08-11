package com.b1.place.dto;

import com.b1.place.entity.Place;
import com.b1.place.entity.PlaceStatus;
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
    private PlaceStatus status;

    public static PlaceGetResponseDto of(final Place place) {
        return PlaceGetResponseDto.builder()
                .placeId(place.getId())
                .location(place.getLocation())
                .name(place.getName())
                .maxSeat(place.getMaxSeat())
                .status(place.getStatus())
                .build();
    }

}
