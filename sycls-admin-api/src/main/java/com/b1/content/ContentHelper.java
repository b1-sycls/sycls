package com.b1.content;

import com.b1.content.dto.ContentDetailImagePathGetAdminResponseDto;
import com.b1.content.dto.ContentGetAdminResponseDto;
import com.b1.content.dto.ContentSearchCondRequest;
import com.b1.content.entity.Content;
import com.b1.content.entity.ContentDetailImage;
import com.b1.exception.customexception.ContentNotFoundException;
import com.b1.exception.errorcode.ContentErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Content Helper")
@Component
@RequiredArgsConstructor
public class ContentHelper {

    private final ContentRepository contentRepository;
    private final ContentQueryRepository queryRepository;

    public boolean existsByCategoryId(Long categoryId) {
        return contentRepository.existsByCategoryId(categoryId);
    }

    public void saveContent(Content content) {
        contentRepository.save(content);
    }

    public Content getContent(Long contentId) {
        return contentRepository.findById(contentId)
                .orElseThrow(
                        () -> new ContentNotFoundException(ContentErrorCode.CONTENT_NOT_FOUND));
    }

    public List<ContentDetailImage> getByContentDetailImagesByContentId(Long contentId) {
        return queryRepository.getByContentDetailImagesByContentId(contentId);
    }

    public ContentGetAdminResponseDto getContentByContentId(Long contentId) {
        return queryRepository.getByContentByContentIdForAdmin(contentId);
    }

    public List<ContentDetailImagePathGetAdminResponseDto> getAllContentDetailImagesPathByContentId(
            Long contentId) {
        return queryRepository.getAllContentDetailImagesPathByContentIdForAdmin(contentId);
    }

    public Page<ContentGetAdminResponseDto> getAllContentForAdmin(ContentSearchCondRequest request,
            Pageable pageable) {
        return queryRepository.getAllContentForAdmin(request.getCategoryId(),
                request.getTitleKeyword(), request.getStatus(), pageable);
    }
}
