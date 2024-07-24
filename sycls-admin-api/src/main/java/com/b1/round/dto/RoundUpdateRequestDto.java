package com.b1.round.dto;

import com.b1.round.entity.RoundStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record RoundUpdateRequestDto(

        @NotNull(message = "상태가 누락되었습니다.")
        RoundStatus status,

        @NotNull(message = "공연 날짜는 누락될 수 없습니다.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate startDate,

        @NotNull(message = "공연 시작 시간은 누락될 수 없습니다.")
        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime,

        @NotNull(message = "공연 종료 시간은 누락될 수 없습니다.")
        @JsonFormat(pattern = "HH:mm")
        LocalTime endTime

) {

}
