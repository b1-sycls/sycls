package com.b1.place;

import com.b1.common.PageResponseDto;
import com.b1.place.dto.PlaceAddRequestDto;
import com.b1.place.dto.PlaceGetResponseDto;
import com.b1.place.dto.PlaceSearchCondRequestDto;
import com.b1.place.dto.PlaceUpdateRequestDto;
import com.b1.place.entity.Place;
import com.b1.place.entity.PlaceStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "Place Service")
@Service
@RequiredArgsConstructor
@Transactional
public class PlaceService {

    private final PlaceHelper placeHelper;

    /**
     * 공연장 등록
     */
    public void addPlace(final PlaceAddRequestDto requestDto) {
        Place place = Place.addPlace(
                requestDto.location(),
                requestDto.maxSeat(),
                requestDto.name()
        );

        placeHelper.savePlace(place);
    }

    /**
     * 공연장 전체 조회
     */
    @Transactional(readOnly = true)
    public PageResponseDto<PlaceGetResponseDto> getAllPlaces(
            final PlaceSearchCondRequestDto requestDto) {
        Sort.Direction direction = requestDto.getIsDesc() ? Direction.DESC : Direction.ASC;
        //TODO 음수 검증
        Pageable pageable = PageRequest.of(requestDto.getPageNum() - 1, requestDto.getPageSize(),
                direction, requestDto.getOrderBy());

        Page<PlaceGetResponseDto> pageResponseDto = placeHelper.getAllPlaces(requestDto, pageable);
        return PageResponseDto.of(pageResponseDto);
    }

    /**
     * 공연장 단건 조회
     */
    @Transactional(readOnly = true)
    public PlaceGetResponseDto getPlace(final Long placeId) {
        Place place = placeHelper.getPlace(placeId);
        return PlaceGetResponseDto.of(place);
    }

    /**
     * 공연장 수정
     */
    public Long updatePlace(final Long placeId, final PlaceUpdateRequestDto requestDto) {
        Place place = placeHelper.getPlace(placeId);
        place.updatePlace(requestDto.location(), requestDto.name(),
                requestDto.maxSeat(), requestDto.status());
        return place.getId();
    }

    /**
     * 공연장 삭제
     */
    public void deletePlace(Long placeId) {
        Place place = placeHelper.getPlace(placeId);
        PlaceStatus.checkDeleted(place.getStatus());
        place.deletePlace();
    }
}
