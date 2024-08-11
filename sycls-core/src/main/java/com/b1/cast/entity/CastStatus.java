package com.b1.cast.entity;

import com.b1.exception.customexception.CastAlreadyCanceledException;
import com.b1.exception.errorcode.CastErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Cast Status")
@Getter
@RequiredArgsConstructor
public enum CastStatus {

    SCHEDULED("SCHEDULED"), // 출연 예정
    CANCELED("CANCELED"), // 취소
    ;

    private final String value;

    public static void checkCanceled(final CastStatus status) {
        if (status.equals(CANCELED)) {
            log.error("이미 취소된 출연자");
            throw new CastAlreadyCanceledException(CastErrorCode.CAST_ALREADY_CANCELED);
        }
    }
}
