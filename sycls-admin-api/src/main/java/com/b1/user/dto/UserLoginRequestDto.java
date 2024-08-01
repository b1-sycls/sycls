package com.b1.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public record UserLoginRequestDto(
        @Length(max = 30, message = "이메일은 최대 30자까지 입력 가능합니다.")
        @Email(message = "유효한 이메일 형식이 아닙니다.")
        @NotBlank(message = "이메일은 필수 항목입니다.")
        String email,

        @Size(max = 20, message = "비밀번호는 20자 이하로 입력해야 합니다.")
        @NotBlank(message = "비밀번호는 필수 항목입니다.")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*]).{1,20}$",
                message = "비밀번호는 숫자와 특수문자를 하나 이상 포함해야 합니다."
        )
        String password
) {

}