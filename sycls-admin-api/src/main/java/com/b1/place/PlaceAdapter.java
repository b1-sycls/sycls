package com.b1.place;

import com.b1.place.dto.PlaceGetResponseDto;
import com.b1.place.dto.PlaceSearchCondiRequestDto;
import com.b1.place.entity.Place;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Slf4j(topic = "PlaceAdapter")
@Component
@RequiredArgsConstructor
public class PlaceAdapter {

    private final PlaceRepository placeRepository;

    /**
     * 공연장 등록
     */
    public void savePlace(final Place place) {
        placeRepository.save(place);
    }

    /**
     * 공연장 전체 조회
     */
    public Page<PlaceGetResponseDto> getAllPlaces(final PlaceSearchCondiRequestDto requestDto,
            final Pageable pageable) {
        return placeRepository.getAllPlaces(requestDto.getLocation(),
                requestDto.getName(), requestDto.getMaxSeat(), pageable);
    }
}
