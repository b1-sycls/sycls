package com.b1.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record FindEmailRequestDto(
        @Size(min = 2, max = 20, message = "사용자 이름은 2자 이상 20자 이하로 입력해야 합니다.")
        @NotBlank(message = "사용자 이름은 필수 항목입니다.")
        String username,
        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "유효한 전화번호 형식이 아닙니다.")
        @NotBlank(message = "전화번호는 필수 항목입니다.")
        String phoneNumber
) {

}
