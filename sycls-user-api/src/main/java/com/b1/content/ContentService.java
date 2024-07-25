package com.b1.content;

import com.b1.content.dto.ContentDetailImagePathGetUserResponseDto;
import com.b1.content.dto.ContentDetailResponseDto;
import com.b1.content.dto.ContentGetUserResponseDto;
import com.b1.round.RoundHelper;
import com.b1.round.dto.RoundInfoGetUserResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "Content Service")
@Service
@RequiredArgsConstructor
@Transactional
public class ContentService {

    private final ContentHelper contentHelper;
    private final RoundHelper roundHelper;

    @Transactional(readOnly = true)
    public ContentDetailResponseDto getContent(Long contentId) {

        ContentGetUserResponseDto contentGetUser = contentHelper.getContentByContentId(contentId);

        List<ContentDetailImagePathGetUserResponseDto> contentDetailImagePathList =
                contentHelper.getAllContentDetailImagesPathByContentId(contentId);

        List<RoundInfoGetUserResponseDto> roundInfoList = roundHelper.getAllRoundsInfoByContentId(
                contentId);

        return ContentDetailResponseDto.of(contentGetUser, contentDetailImagePathList,
                roundInfoList);
    }
}
