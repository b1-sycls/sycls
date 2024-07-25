package com.b1.s3;

import com.b1.exception.customexception.S3InvalidImageTypeException;
import com.b1.exception.errorcode.S3ErrorCode;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j(topic = "S3 Util")
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class S3Util {

    private static final String URL_PREFIX = "content";

    public static boolean isFileExists(MultipartFile multipartFile) {
        return multipartFile != null && !multipartFile.isEmpty();
    }

    public static String getCheckImageExtension(String fileName) {
        List<String> validExtensionList = Arrays.asList("jpg", "jpeg", "png", "webp");

        int extensionIndex = fileName.lastIndexOf(".");

        String extension = fileName.substring(extensionIndex + 1).toLowerCase();

        if (!validExtensionList.contains(extension)) {
            log.error("이미지 타입 불일치 | fileName : {}", fileName);
            throw new S3InvalidImageTypeException(S3ErrorCode.S3_INVALID_IMAGE_TYPE);
        }

        return extension;
    }

    public static String createImageDir(S3Type type) {
        return URL_PREFIX + "/"
                + type.getValue() + "/";
    }

    public static String createFileName(String extension) {
        return UUID.randomUUID().toString().concat(extension);
    }
}
