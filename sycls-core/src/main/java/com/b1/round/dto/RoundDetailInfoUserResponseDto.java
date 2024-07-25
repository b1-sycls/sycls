package com.b1.round.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoundDetailInfoUserResponseDto {

    // 나중에 출연진 추가를위해 미리 같은 필드의 dto 선언
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
}
