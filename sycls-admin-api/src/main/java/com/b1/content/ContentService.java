package com.b1.content;

import com.b1.S3.S3Uploader;
import com.b1.category.CategoryHelper;
import com.b1.category.entity.Category;
import com.b1.content.dto.ContentAddRequestDto;
import com.b1.content.dto.ContentUpdateRequestDto;
import com.b1.content.entity.Content;
import com.b1.content.entity.ContentDetailImage;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j(topic = "Content Service")
@Service
@RequiredArgsConstructor
@Transactional
public class ContentService {

    private final ContentHelper contentHelper;
    private final CategoryHelper categoryHelper;
    private final S3Uploader s3Uploader;

    // TODO 상태추가후 로직 추가
    public void addContent(ContentAddRequestDto requestDto, MultipartFile mainImage,
            MultipartFile[] detailImages) {

        Category category = categoryHelper.findById(requestDto.categoryId());

        Content content = Content.addContent(
                requestDto.title(),
                requestDto.description(),
                category
        );

        String mainImagePath = s3Uploader.saveMainImage(mainImage);

        content.addMainImagePath(mainImagePath);

        if (detailImages == null) {
            contentHelper.saveContent(content);
            return;
        }

        List<String> detailImageList = s3Uploader.saveDetailImage(detailImages);

        List<ContentDetailImage> contentDetailImageList = getContentDetailImages(detailImageList,
                content);

        content.addContentDetailImageList(contentDetailImageList);

        contentHelper.saveContent(content);
    }

    public void updateContent(Long contentId, ContentUpdateRequestDto requestDto,
            MultipartFile mainImage, MultipartFile[] detailImages) {

        Content content = contentHelper.findById(contentId);

        Category category = categoryHelper.findById(requestDto.categoryId());

        String contentMainImagePath = content.getMainImagePath();

        if (mainImage != null) {
            s3Uploader.deleteFileFromS3(requestDto.oldMainImagePath());
            contentMainImagePath = s3Uploader.saveMainImage(mainImage);
        }

        List<ContentDetailImage> detailImageList = contentHelper.getByContentDetailImagesByContentId(
                content.getId());

        if (detailImages != null) {
            for (String oldDetailImagePath : requestDto.detailImagePaths()) {
                s3Uploader.deleteFileFromS3(oldDetailImagePath);
            }

            for (ContentDetailImage detailImage : detailImageList) {
                detailImage.disableStatus();
            }

            List<String> newDetailImageList = s3Uploader.saveDetailImage(detailImages);
            detailImageList = getContentDetailImages(newDetailImageList, content);
        }

        content.updateContent(category, requestDto.title(), requestDto.description(),
                contentMainImagePath, detailImageList);
    }

    private List<ContentDetailImage> getContentDetailImages(List<String> detailImageList,
            Content content) {
        List<ContentDetailImage> contentDetailImageList = new ArrayList<>();

        for (String detailImagePath : detailImageList) {
            ContentDetailImage contentDetailImage = ContentDetailImage.addContentDetailImage(
                    detailImagePath,
                    content
            );

            contentDetailImageList.add(contentDetailImage);
        }
        return contentDetailImageList;
    }
}
