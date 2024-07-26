package com.b1.content;

import com.b1.content.dto.ContentDetailImagePathGetUserResponseDto;
import com.b1.content.dto.ContentGetUserResponseDto;
import com.b1.content.dto.ContentSearchCondRequest;
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
    public Page<ContentGetUserResponseDto> getAllContentForAdmin(
            final ContentSearchCondRequest request,
            final Pageable pageable) {
        return contentQueryRepository.getAllContentForUser(request.getCategoryId(),
                request.getTitleKeyword(), pageable);
    }
}


