package com.b1.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReviewAddRequestDto(
        @NotBlank(message = "내용이 누락되었습니다.")
        @Size(max = 500, message = "최대 500자까지 입력 가능합니다.")
        String comment,

        @NotNull(message = "평점이 누락되었습니다.")
        @Max(value = 10, message = "폄점은 10점을 넘길 수 없습니다.")
        @Min(value = 0, message = "평점은 0보다 작을 수 없습니다.")
        Integer rating
) {

}
