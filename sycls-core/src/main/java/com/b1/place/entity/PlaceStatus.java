package com.b1.place.entity;

import com.b1.exception.customexception.PlaceAlreadyDeletedException;
import com.b1.exception.errorcode.PlaceErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Place Status")
@Getter
@RequiredArgsConstructor
public enum PlaceStatus {
    ENABLE("ENABLE"),
    INACTIVATED("INACTIVATED"),
    DISABLE("DISABLE"),
    ;

    private final String value;

    public static void checkDeleted(final PlaceStatus status) {
        if (status.equals(DISABLE)) {
            log.error("삭제된 상태 | {}", status.value);
            throw new PlaceAlreadyDeletedException(PlaceErrorCode.ALREADY_DELETED);
        }
    }
}
