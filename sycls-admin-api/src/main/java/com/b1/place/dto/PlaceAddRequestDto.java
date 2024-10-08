package com.b1.place.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PlaceAddRequestDto(
        @NotBlank(message = "장소가 누락되었습니다.")
        @Size(max = 300, message = "최대 300자까지 입력 가능합니다.")
        String location,

        @NotBlank(message = "공연장 이름이 누락되었습니다.")
        @Size(max = 50, message = "최대 50자까지 입력 가능합니다.")
        String name,

        @NotNull(message = "공연장 최대 좌석수가 누락되었습니다.")
        @Max(value = 1000000000, message = "최대 좌석수는 10억석을 넘을 수 없습니다.")
        @Min(value = 0, message = "최대 좌석수는 0보다 작을 수 없습니다.")
        Integer maxSeat
) {

}
