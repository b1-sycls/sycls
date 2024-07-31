package com.b1.content;

import com.b1.content.dto.ContentDetailImagePathGetUserResponseDto;
import com.b1.content.dto.ContentGetUserResponseDto;
import com.b1.content.entity.Content;
import com.b1.content.entity.ContentStatus;
import com.b1.exception.customexception.ContentNotFoundException;
import com.b1.exception.errorcode.ContentErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Content Helper ")
@Component
@RequiredArgsConstructor
public class ContentHelper {

    private final ContentQueryRepository contentQueryRepository;
    private final ContentRepository contentRepository;

    /**
     * 단일 조회시 필요한 공연의 정보 조회
     */
    public ContentGetUserResponseDto getContentByContentId(final Long contentId) {
        return contentQueryRepository.getByContentByContentIdForUser(contentId);
    }

    /**
     * 단일 조회시 필요한 공연의 서브이미지 정보 조회
     */
    public List<ContentDetailImagePathGetUserResponseDto> getAllContentDetailImagesPathByContentId(
            final Long contentId) {
        return contentQueryRepository.getAllContentDetailImagesPathByContentIdForUser(contentId);
    }

    /**
     * 단일 조회시 필요한 공연의 회차 정보 페이징
     */
    public Page<ContentGetUserResponseDto> getAllContentForUser(final Long categoryId,
            final String titleKeyword, final Pageable pageable) {
        return contentQueryRepository.getAllContentForUser(categoryId, titleKeyword, pageable);
    }

    /**
     * 리뷰 등록을 위한 공연 조회
     */
    public Content getContentForReview(final Long contentId) {
        Content content = contentRepository.findById(contentId).orElseThrow(
                () -> {
                    log.error("찾을 수 없는 공연 | {}", contentId);
                    return new ContentNotFoundException(ContentErrorCode.CONTENT_NOT_FOUND);
                }
        );
        ContentStatus.unVisible(content.getStatus());
        return content;
    }
}


