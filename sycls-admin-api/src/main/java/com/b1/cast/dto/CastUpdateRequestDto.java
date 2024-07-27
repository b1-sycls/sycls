package com.b1.cast.dto;

import com.b1.cast.entity.CastStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CastUpdateRequestDto(

        @NotNull(message = "회차 아이디는 누락되었습니다.")
        Long roundId,

        @NotBlank(message = "출연자 이름은 공백을 수 없습니다.")
        @Size(max = 50, message = "출연자 이름은 50자 이하여야합니다.")
        String name,

        @NotNull(message = "출연자 상태는 공백일 수 없습니다.")
        CastStatus status
) {

}
