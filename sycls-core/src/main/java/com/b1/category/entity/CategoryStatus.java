package com.b1.category.entity;

import com.b1.exception.customexception.CategoryAlreadyDeletedException;
import com.b1.exception.errorcode.CategoryErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Category Status")
@Getter
@RequiredArgsConstructor
public enum CategoryStatus {

    ENABLE("ENABLE"),
    DISABLE("DISABLE"),
    ;

    private final String value;

    public static void checkDeleted(CategoryStatus status) {
        if (status.equals(DISABLE)) {
            log.error("이미 삭제된 카테고리 | request : {}", status);
            throw new CategoryAlreadyDeletedException(CategoryErrorCode.CATEGORY_ALREADY_DELETED);
        }
    }

}
