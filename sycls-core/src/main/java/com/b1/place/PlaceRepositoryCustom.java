package com.b1.place;

import com.b1.place.dto.PlaceGetResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceRepositoryCustom {

    Page<PlaceGetResponseDto> getAllPlaces(String location, String name,
            Integer maxSeat, Pageable pageable);

}
