package com.b1.content;

import com.b1.common.PageResponseDto;
import com.b1.content.dto.ContentDetailImagePathGetUserResponseDto;
import com.b1.content.dto.ContentDetailResponseDto;
import com.b1.content.dto.ContentGetUserResponseDto;
import com.b1.round.RoundHelper;
import com.b1.round.dto.RoundInfoGetUserResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Transactional(readOnly = true)
    public PageResponseDto<ContentGetUserResponseDto> getAllContents(Long categoryId,
            String titleKeyword, int page, String sortProperty, String sortDirection) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortProperty);

        Pageable pageable = PageRequest.of(page - 1, 4, sort);

        Page<ContentGetUserResponseDto> pageResponseDto = contentHelper.getAllContentForAdmin(
                categoryId, titleKeyword, pageable);

        return PageResponseDto.of(pageResponseDto);
    }
}
