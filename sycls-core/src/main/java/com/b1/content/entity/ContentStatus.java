package com.b1.content.entity;

import com.b1.exception.customexception.ContentStatusEqualsException;
import com.b1.exception.customexception.ReviewCannotAddException;
import com.b1.exception.errorcode.ContentErrorCode;
import com.b1.exception.errorcode.ReviewErrorCode;
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

    public static void checkStatusEquals(
            final ContentStatus firstStatus,
            final ContentStatus secondStatus
    ) {
        if (firstStatus.equals(secondStatus)) {
            log.error("공연의 상태가 동일 | status : {} | {}", firstStatus, secondStatus);
            throw new ContentStatusEqualsException(ContentErrorCode.CONTENT_STATUS_EQUALS);
        }
    }

    public static boolean isVisible(final ContentStatus status) {
        return status.equals(VISIBLE);
    }

    public static void unVisible(final ContentStatus status) {
        if (!VISIBLE.equals(status)) {
            log.error("리뷰 등록 불가능한 공연 | {}", status);
            throw new ReviewCannotAddException(ReviewErrorCode.CANNOT_ADD_REVIEW);
        }
    }

}
