package com.b1.place.dto;

import com.b1.place.entity.PlaceStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PlaceUpdateRequestDto(
        @NotBlank(message = "장소가 누락되었습니다.")
        @Size(max = 300, message = "최대 300자까지 입력 가능합니다.")
        String location,

        @NotBlank(message = "공연장 이름이 누락되었습니다.")
        @Size(max = 50, message = "최대 50자까지 입력 가능합니다.")
        String name,

        @NotNull(message = "공연장 최대 좌석수가 누락되었습니다.")
        Integer maxSeat,

        @NotNull(message = "공연장 상태가 누락되었습니다.")
        PlaceStatus status
) {

}
