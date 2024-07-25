package com.b1.s3;

import com.b1.constant.S3Constant;
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

    /**
     * 이미지 누락 확인
     */
    public static boolean isFileExists(final MultipartFile multipartFile) {
        return multipartFile != null && !multipartFile.isEmpty();
    }

    /**
     * 이미지 파일 타입 확인
     */
    public static String getCheckImageExtension(final String fileName) {
        List<String> validExtensionList = Arrays.asList(S3Constant.VALID_EXTENSIONS);

        int extensionIndex = fileName.lastIndexOf(".");

        String extension = fileName.substring(extensionIndex + 1).toLowerCase();

        if (!validExtensionList.contains(extension)) {
            log.error("이미지 타입 불일치 | fileName : {}", fileName);
            throw new S3InvalidImageTypeException(S3ErrorCode.S3_INVALID_IMAGE_TYPE);
        }

        return extension;
    }

    /**
     * 이미지 경로 생성
     */
    public static String createImageDir(final S3Type type) {
        return S3Constant.URL_PREFIX + "/"
                + type.getValue() + "/";
    }

    /**
     * 이미지 파일 UUID 생성
     */
    public static String createFileName(final String extension) {
        return UUID.randomUUID().toString().concat(extension);
    }

    /**
     * 이미지 파일 경로에 리전 주소값 제거
     */
    public static String subStringImageDir(final String imageDir) {
        return imageDir.substring(
                imageDir.lastIndexOf(S3Constant.SPLIT_STR) + S3Constant.SPLIT_STR.length());
    }

    /**
     * 이미지 파일 경로에 리전 주소값 추가
     */
    public static String makeResponseImageDir(final String imageDir) {
        return S3Constant.S3_BASE_URL + imageDir;
    }
}