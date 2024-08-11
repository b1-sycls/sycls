package com.b1.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryUpdateRequestDto(
        @NotBlank(message = "카테고리 이름이 누락되었습니다.")
        @Size(max = 100, message = "카테고리 이름은 100자 이하여야 합니다.")
        String name
) {

}
