package com.b1.place;

import com.b1.exception.customexception.PlaceCannotUpdateException;
import com.b1.exception.customexception.PlaceNotFoundException;
import com.b1.exception.errorcode.PlaceErrorCode;
import com.b1.place.dto.PlaceGetResponseDto;
import com.b1.place.dto.PlaceSearchCondRequestDto;
import com.b1.place.entity.Place;
import com.b1.place.entity.PlaceStatus;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Place Helper")
@Component
@RequiredArgsConstructor
public class PlaceHelper {

    private final PlaceRepository placeRepository;
    private final PlaceQueryRepository placeQueryRepository;

    /**
     * 공연장 등록
     */
    public void savePlace(final Place place) {
        placeRepository.save(place);
    }

    /**
     * 공연장 전체 조회
     */
    public Page<PlaceGetResponseDto> getAllPlaces(
            final PlaceSearchCondRequestDto requestDto,
            final Pageable pageable
    ) {
        return placeQueryRepository.getAllPlaces(
                requestDto.getLocation(),
                requestDto.getName(),
                requestDto.getStatus(),
                pageable
        );
    }

    /**
     * 공연장 단건 조회
     */
    public Place getPlace(final Long placeId) {
        return placeRepository.findById(placeId).orElseThrow(
                () -> {
                    log.error("존재하지 않는 공연장 | {}", placeId);
                    return new PlaceNotFoundException(PlaceErrorCode.NOT_FOUND_PLACE);
                }
        );
    }

    /**
     * 공연장 존재 확인
     */
    public void existPlace(final Long placeId) {
        if (!placeRepository.existsById(placeId)) {
            log.error("존재하지 않는 공연장 | {}", placeId);
            throw new PlaceNotFoundException(PlaceErrorCode.NOT_FOUND_PLACE);
        }
    }

    /**
     * 공연장 최대 좌석 수 및 총 좌석수 불러오기
     */
    public void checkMaxSeatAndSeatCount(final Long placeId, final Integer maxSeat) {
        Long seatCount = placeQueryRepository.getSeatCount(placeId);
        if (!Objects.equals(maxSeat.longValue(), seatCount)) {
            log.error("공연장을 활성화할 수 없습니다.| {}", placeId);
            throw new PlaceCannotUpdateException(PlaceErrorCode.CANNOT_UPDATE_PLACE);
        }
    }

    /**
     * 활성화 상태인 공연장만 조회
     */
    public Place getPlaceByEnable(final Long placeId) {
        return placeRepository.findByIdAndStatus(placeId, PlaceStatus.ENABLE)
                .orElseThrow(() -> {
                    log.error("활성화된 공연장이 없음 | {}", placeId);
                    return new PlaceNotFoundException(PlaceErrorCode.NOT_FOUND_PLACE);
                });
    }

    /**
     * 총좌석수, 최대 좌석수 비교
     */
    public Boolean checkMaxSeatAndSeatCountForSeatDelete(
            final Long placeId,
            final Integer maxSeat
    ) {
        Long seatCount = placeQueryRepository.getSeatCount(placeId);
        return maxSeat.longValue() != seatCount;
    }
}
