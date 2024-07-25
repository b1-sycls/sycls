package com.b1.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public record UserVerificationCodeRequestDto(
        @Length(max = 30, message = "이메일은 최대 30자까지 입력 가능합니다.")
        @Email(message = "유효한 이메일 형식이 아닙니다.")
        @NotBlank(message = "이메일은 필수 항목입니다.")
        String email,

        @Size(min = 1, max = 6, message = "코드는 최소 1자 이상, 최대 6자 이하로 입력해야 합니다.")
        @NotBlank(message = "인증 코드는 필수 항목입니다.")
        String code
) {

}