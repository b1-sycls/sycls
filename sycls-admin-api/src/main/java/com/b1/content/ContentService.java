package com.b1.content;

import com.b1.category.CategoryHelper;
import com.b1.category.entity.Category;
import com.b1.common.PageResponseDto;
import com.b1.content.dto.ContentAddRequestDto;
import com.b1.content.dto.ContentDetailImagePathGetAdminResponseDto;
import com.b1.content.dto.ContentDetailResponseDto;
import com.b1.content.dto.ContentGetAdminResponseDto;
import com.b1.content.dto.ContentSearchCondRequest;
import com.b1.content.dto.ContentUpdateRequestDto;
import com.b1.content.dto.ContentUpdateStatusRequestDto;
import com.b1.content.entity.Content;
import com.b1.content.entity.ContentDetailImage;
import com.b1.content.entity.ContentStatus;
import com.b1.exception.customexception.InvalidPageNumberException;
import com.b1.exception.errorcode.PageErrorCode;
import com.b1.round.RoundHelper;
import com.b1.round.dto.RoundInfoGetAdminResponseDto;
import com.b1.s3.S3Uploader;
import com.b1.s3.S3Util;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final RoundHelper roundHelper;
    private final S3Uploader s3Uploader;

    /**
     * 공연 등록 기능
     */
    public void addContent(final ContentAddRequestDto requestDto, final MultipartFile mainImage,
            final MultipartFile[] detailImages) {

        Category category = categoryHelper.findById(requestDto.categoryId());

        Content content = Content.addContent(
                requestDto.title(),
                requestDto.description(),
                category
        );

        final String mainImagePath = s3Uploader.saveMainImage(mainImage);

        content.addMainImagePath(mainImagePath);

        if (detailImages == null) {
            contentHelper.saveContent(content);
            return;
        }

        final List<String> detailImageList = s3Uploader.saveDetailImage(detailImages);

        final List<ContentDetailImage> contentDetailImageList = getAndCreateContentDetailImages(
                detailImageList, content);

        content.addContentDetailImageList(contentDetailImageList);

        contentHelper.saveContent(content);
    }

    /**
     * 공연 정보 수정 기능
     */
    public void updateContent(final Long contentId, final ContentUpdateRequestDto requestDto,
            final MultipartFile mainImage, final MultipartFile[] detailImages) {

        Content content = contentHelper.getContent(contentId);

        Category category = categoryHelper.findById(requestDto.categoryId());

        String contentMainImagePath = content.getMainImagePath();

        if (mainImage != null) {
            contentMainImagePath = s3Uploader.saveMainImage(mainImage);
        }

        List<ContentDetailImage> detailImageList = contentHelper.getByContentDetailImagesByContentId(
                content.getId());

        if (detailImages != null) {

            for (ContentDetailImage detailImage : detailImageList) {
                detailImage.disableStatus();
            }

            final List<String> newDetailImageList = s3Uploader.saveDetailImage(detailImages);
            detailImageList = getAndCreateContentDetailImages(newDetailImageList, content);
        }

        content.updateContent(category, requestDto.title(), requestDto.description(),
                contentMainImagePath, detailImageList);
    }

    /**
     * 공연 상태 수정 기능
     */
    public void updateContentStatus(final Long contentId,
            final ContentUpdateStatusRequestDto requestDto) {

        Content content = contentHelper.getContent(contentId);

        ContentStatus.checkStatusEquals(content.getStatus(), requestDto.status());

        if (requestDto.status() == ContentStatus.VISIBLE) {
            contentHelper.checkRoundStatusByContentId(content.getId());
        }

        content.updateStatus(requestDto.status());
    }

    /**
     * 공연 단일 조회 기능
     */
    @Transactional(readOnly = true)
    public ContentDetailResponseDto getContent(final Long contentId) {

        ContentGetAdminResponseDto contentGetAdmin = contentHelper.getContentByContentId(
                contentId);

        contentGetAdmin.updateImagePath(S3Util.makeResponseImageDir(
                contentGetAdmin.getMainImagePath()));

        List<ContentDetailImagePathGetAdminResponseDto> contentDetailImagePathList =
                contentHelper.getAllContentDetailImagesPathByContentId(contentId);

        for (ContentDetailImagePathGetAdminResponseDto dto : contentDetailImagePathList) {
            final String detailImagePath = S3Util.makeResponseImageDir(dto.getDetailImagePath());
            dto.updateDetailImagePath(detailImagePath);
        }

        List<RoundInfoGetAdminResponseDto> roundInfoList = roundHelper.getAllRoundsInfoByContentId(
                contentId);

        return ContentDetailResponseDto.of(contentGetAdmin, contentDetailImagePathList,
                roundInfoList);
    }

    /**
     * 공연 전체 조회 기능
     */
    @Transactional(readOnly = true)
    public PageResponseDto<ContentGetAdminResponseDto> getAllContents(
            final ContentSearchCondRequest request) {

        Sort sort = Sort.by(Sort.Direction.fromString(request.getSortDirection()),
                request.getSortProperty());

        if (request.getPage() <= 0) {
            log.error("페이지 값이 0이하 request : {}", request.getPage());
            throw new InvalidPageNumberException(PageErrorCode.INVALID_PAGE_NUMBER);
        }

        Pageable pageable = PageRequest.of(request.getPage() - 1, 4, sort);

        Page<ContentGetAdminResponseDto> pageResponseDto = contentHelper.getAllContentForAdmin(
                request, pageable);

        for (ContentGetAdminResponseDto dto : pageResponseDto) {
            dto.updateImagePath(S3Util.makeResponseImageDir(dto.getMainImagePath()));
        }

        return PageResponseDto.of(pageResponseDto);
    }

    /**
     * 공연 서브 이미지 생성로직
     */
    private List<ContentDetailImage> getAndCreateContentDetailImages(
            final List<String> detailImageList, final Content content) {

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
