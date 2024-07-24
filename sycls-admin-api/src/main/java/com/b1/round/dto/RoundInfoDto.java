package com.b1.round.dto;

import com.b1.round.entity.RoundStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record RoundInfoDto(

        @NotNull(message = "회차 순서는 누락될 수 없습니다.")
        Integer sequence,

        @NotNull(message = "공연장 고유번호가 누락되었습니다.")
        Long placeId,

        List<Long> vipSeatList,

        Integer vipPrice,

        List<Long> royalList,

        Integer royalPrice,

        List<Long> superiorList,

        Integer superiorPrice,

        List<Long> aGradeList,

        Integer aGradePrice,

        @NotNull(message = "회차 상태는 누락될 수 없습니다.")
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
