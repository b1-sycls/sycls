package com.b1.content.dto;

import com.b1.content.entity.RoundStatus;
import jakarta.validation.constraints.NotNull;

public record RoundUpdateStatusRequestDto(

        @NotNull(message = "상태가 누락되었습니다.")
        RoundStatus status
) {

}
