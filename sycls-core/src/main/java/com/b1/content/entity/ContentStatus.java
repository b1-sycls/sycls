package com.b1.content.entity;

import com.b1.exception.customexception.ContentStatusEqualsException;
import com.b1.exception.errorcode.ContentErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Content Status")
@Getter
@RequiredArgsConstructor
public enum ContentStatus {
    HIDDEN("HIDDEN"),
    VISIBLE("VISIBLE"),
    DELETED("DELETED"),
    ;

    private final String value;

    public static void checkStatusEquals(ContentStatus firstStatus, ContentStatus secondStatus) {
        if (firstStatus.equals(secondStatus)) {
            log.error("공연의 상태가 동일 | status : {} | {}", firstStatus, secondStatus);
            throw new ContentStatusEqualsException(ContentErrorCode.CONTENT_STATUS_EQUALS);
        }
    }

    public static boolean isVisible(ContentStatus status) {
        return status.equals(VISIBLE);
    }

}
