package com.b1.round.dto;

import com.b1.round.entity.RoundStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoundSimpleUserResponseDto {

    private final Long roundId;
    private final String contentTitle;
    private final Integer sequence;
    private final LocalDate startDate;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final RoundStatus status;

}
