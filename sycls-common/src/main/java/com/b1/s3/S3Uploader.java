package com.b1.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.b1.exception.customexception.S3MainImageMissingException;
import com.b1.exception.customexception.S3UploadingException;
import com.b1.exception.errorcode.S3ErrorCode;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j(topic = "S3 Uploader")
@Service
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * S3 에 파일 저장 후 저장경로 반환
     */
    public String saveMainImage(final MultipartFile file, final S3UrlPathType path) {
        checkImageIsPresent(file);

        String extension = S3Util.getCheckImageExtension(
                Objects.requireNonNull(file.getOriginalFilename()));

        String imageDir = S3Util.createImageDir(path);

        String uploadFileName = imageDir + S3Util.createFileName(extension);

        return uploadFileToS3(file, uploadFileName);
    }

    /**
     * S3 에 파일들 저장 후 저장경로 반환
     */
    public List<String> saveDetailImage(final MultipartFile[] fileList, final S3UrlPathType path) {
        List<String> subImageList = new ArrayList<>();

        for (MultipartFile file : fileList) {
            checkImageIsPresent(file);

            String extension = S3Util.getCheckImageExtension(
                    Objects.requireNonNull(file.getOriginalFilename())
            );

            String imageDir = S3Util.createImageDir(path);

            String uploadFileName = imageDir + S3Util.createFileName(extension);

            subImageList.add(uploadFileToS3(file, uploadFileName));
        }

        return subImageList;
    }

    /**
     * 이미지 누락 검사
     */
    private void checkImageIsPresent(final MultipartFile file) {
        if (!S3Util.isFileExists(file)) {
            log.error("이미지 누락 오류");
            throw new S3MainImageMissingException(S3ErrorCode.S3_MISSING_IMAGE);
        }
    }

    /**
     * 이미지 업로드 로직
     */
    private String uploadFileToS3(final MultipartFile file, final String uploadFileName) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, uploadFileName, inputStream,
                    objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            log.error("이미지 업로드 중 오류가 발생 | fileName : {}", uploadFileName);
            throw new S3UploadingException(S3ErrorCode.S3_UPLOADING_FAIL);
        }

        return S3Util.subStringImageDir(amazonS3Client.getUrl(bucket, uploadFileName).toString());
    }

    /**
     * 이미지 삭제 로직
     */
    public void deleteFileFromS3(final String imageDir) {
        String substringImageDir = S3Util.subStringImageDir(imageDir);

        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, substringImageDir));
    }
}
