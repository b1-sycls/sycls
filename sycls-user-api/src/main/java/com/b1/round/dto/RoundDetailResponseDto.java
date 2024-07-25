package com.b1.round.dto;

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

    // 공연 정보
    private final Long contentId;
    private final String contentTitle;
    private final String description;
    private final String mainImagePath;

    // 카테고리 정보
    private final Long categoryId;
    private final String categoryName;

    // 공연장 정보
    private final Long placeId;
    private final String placeName;
    private final String placeLocation;

    // TODO 출연진 정보 List

    // 좌석 등급 정보?

    // 티켓정보?

    public static RoundDetailResponseDto of(final RoundDetailInfoUserResponseDto responseDto) {
        return RoundDetailResponseDto.builder()
                .roundId(responseDto.getRoundId())
                .sequence(responseDto.getSequence())
                .startDate(responseDto.getStartDate())
                .startTime(responseDto.getStartTime())
                .endTime(responseDto.getEndTime())
                .contentId(responseDto.getContentId())
                .contentTitle(responseDto.getContentTitle())
                .description(responseDto.getDescription())
                .mainImagePath(responseDto.getMainImagePath())
                .categoryId(responseDto.getCategoryId())
                .categoryName(responseDto.getCategoryName())
                .placeId(responseDto.getPlaceId())
                .placeName(responseDto.getPlaceName())
                .placeLocation(responseDto.getPlaceLocation())
                .build();
    }
}
