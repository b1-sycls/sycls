package com.b1.content;

import com.b1.content.dto.ContentDetailImagePathGetUserResponseDto;
import com.b1.content.dto.ContentGetUserResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Content Helper ")
@Component
@RequiredArgsConstructor
public class ContentHelper {

    private final ContentQueryRepository queryRepository;


    public ContentGetUserResponseDto getContentByContentId(Long contentId) {
        return queryRepository.getByContentByContentIdForUser(contentId);
    }

    public List<ContentDetailImagePathGetUserResponseDto> getAllContentDetailImagesPathByContentId(
            Long contentId) {
        return queryRepository.getAllContentDetailImagesPathByContentIdForUser(contentId);
    }
}


