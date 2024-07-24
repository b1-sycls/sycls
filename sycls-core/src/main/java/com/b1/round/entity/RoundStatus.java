package com.b1.round.entity;

import com.b1.exception.customexception.RoundStatusEqualsException;
import com.b1.exception.errorcode.RoundErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Round Status")
@Getter
@RequiredArgsConstructor
public enum RoundStatus {
    WAITING("WAITING"),    // 대기
    AVAILABLE("AVAILABLE"),  // 예매 가능
    CLOSED("CLOSED"),  // 마감
    ;

    private final String value;

    public static void checkClosed(RoundStatus status) {
        if (status.equals(RoundStatus.CLOSED)) {
            log.error("해당 회차가 이미 닫혀있는 상태 request : {}", status);
            throw new RoundStatusEqualsException(RoundErrorCode.STATUS_ALREADY_CLOSED);
        }
    }
}
