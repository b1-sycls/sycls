package com.b1.review.entity;

import com.b1.exception.customexception.ReviewAlreadyDeletedException;
import com.b1.exception.errorcode.ReviewErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Review Status")
@Getter
@RequiredArgsConstructor
public enum ReviewStatus {
    ENABLE("ENABLE"),
    DISABLE("DISABLE"),
    ;

    private final String value;

    public static void checkDeleted(final ReviewStatus status) {
        if (status.equals(DISABLE)) {
            log.error("삭제된 상태. | {}", status);
            throw new ReviewAlreadyDeletedException(ReviewErrorCode.ALREADY_DELETED);
        }
    }
}
