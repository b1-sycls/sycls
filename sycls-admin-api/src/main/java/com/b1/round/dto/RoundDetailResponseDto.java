package com.b1.round.dto;

import com.b1.category.entity.CategoryStatus;
import com.b1.content.entity.ContentStatus;
import com.b1.place.entity.PlaceStatus;
import com.b1.round.entity.RoundStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RoundDetailResponseDto {

    // 회차 정보
    private final Long roundId;
    private final Integer sequence;
    private final LocalDate startDate;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final RoundStatus roundStatus;

    // 공연 정보
    private final Long contentId;
    private final String contentTitle;
    private final String description;
    private final String mainImagePath;
    private final ContentStatus contentStatus;

    // 카테고리 정보
    private final Long categoryId;
    private final String categoryName;
    private final CategoryStatus categoryStatus;

    // 공연장 정보
    private final Long placeId;
    private final String placeName;
    private final String placeLocation;
    private final PlaceStatus placeStatus;

    // TODO 출연진 정보 List

    // 좌석 등급 정보?

    // 티켓정보?

    public static RoundDetailResponseDto of(final RoundDetailInfoAdminResponseDto responseDto) {
        return RoundDetailResponseDto.builder()
                .roundId(responseDto.getRoundId())
                .sequence(responseDto.getSequence())
                .startDate(responseDto.getStartDate())
                .startTime(responseDto.getStartTime())
                .endTime(responseDto.getEndTime())
                .roundStatus(responseDto.getRoundStatus())
                .contentId(responseDto.getContentId())
                .contentTitle(responseDto.getContentTitle())
                .description(responseDto.getDescription())
                .mainImagePath(responseDto.getMainImagePath())
                .contentStatus(responseDto.getContentStatus())
                .categoryId(responseDto.getCategoryId())
                .categoryName(responseDto.getCategoryName())
                .categoryStatus(responseDto.getCategoryStatus())
                .placeId(responseDto.getPlaceId())
                .placeName(responseDto.getPlaceName())
                .placeLocation(responseDto.getPlaceLocation())
                .placeStatus(responseDto.getPlaceStatus())
                .build();
    }
}
