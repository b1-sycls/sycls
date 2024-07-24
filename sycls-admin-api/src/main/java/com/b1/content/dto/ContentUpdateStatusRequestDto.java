package com.b1.content.dto;

import com.b1.content.entity.ContentStatus;
import jakarta.validation.constraints.NotNull;

public record ContentUpdateStatusRequestDto(

        @NotNull(message = "상태가 누락되었습니다.")
        ContentStatus status
) {

}
