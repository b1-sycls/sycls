package com.b1.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "사용자를 찾지 못하였습니다."),
    USER_ALREADY_DELETED(HttpStatus.BAD_REQUEST.value(), "삭제된 유저입니다."),
    USER_PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST.value(), "입력한 현재 비밀번호가 올바르지 않습니다."),
    USER_EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), "이메일이 이미 존재합니다."),
    USER_NICKNAME_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), "닉네임이 이미 존재합니다."),
    USER_NOT_ADMIN(HttpStatus.FORBIDDEN.value(), "관리자 권한만 접근 가능합니다.");

    private final Integer httpStatusCode;
    private final String description;
}
