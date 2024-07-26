package com.b1.util;

import com.b1.exception.customexception.InvalidPageNumberException;
import com.b1.exception.errorcode.PageErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Page Util")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageUtil {

    public static void checkPageNumber(int pageNumber) {
        if (pageNumber <= 0) {
            log.error("페이지 값이 0이하 request page number : {}", pageNumber);
            throw new InvalidPageNumberException(PageErrorCode.INVALID_PAGE_NUMBER);
        }
    }
}
