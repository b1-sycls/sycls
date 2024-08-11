package com.b1.category.entity;

import com.b1.exception.customexception.CategoryAlreadyDisableException;
import com.b1.exception.customexception.CategoryAlreadyEnableException;
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

    public static void checkDisable(CategoryStatus status) {
        if (status.equals(DISABLE)) {
            log.error("이미 삭제된 카테고리 | request : {}", status);
            throw new CategoryAlreadyDisableException(CategoryErrorCode.CATEGORY_ALREADY_DISABLE);
        }
    }

    public static void checkEnable(CategoryStatus status) {
        if (status.equals(ENABLE)) {
            log.error("삭제되지 않은 카테고리 | request : {}", status);
            throw new CategoryAlreadyEnableException(CategoryErrorCode.CATEGORY_ALREADY_ENABLE);
        }
    }

}
