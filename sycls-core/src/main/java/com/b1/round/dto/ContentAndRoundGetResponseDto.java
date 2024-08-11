package com.b1.round.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContentAndRoundGetResponseDto {

    // 결제 api 에서 사용
    private final Long contentId;
    private final String contentTitle;
    private final String description;

    private final Long placeId;
    private final String placeName;
    private final String location;

    private final Long roundId;
    private final Integer sequence;
    private final LocalDate startDate;
    private final LocalTime startTime;
    private final LocalTime endTime;
}
