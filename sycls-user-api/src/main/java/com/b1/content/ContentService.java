package com.b1.content;

import com.b1.common.PageResponseDto;
import com.b1.content.dto.ContentDetailImagePathGetUserResponseDto;
import com.b1.content.dto.ContentDetailResponseDto;
import com.b1.content.dto.ContentGetUserResponseDto;
import com.b1.round.RoundHelper;
import com.b1.round.dto.RoundInfoGetUserResponseDto;
import com.b1.s3.S3Util;
import com.b1.util.PageUtil;
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

    /**
     * 공연 단일 조회
     */
    @Transactional(readOnly = true)
    public ContentDetailResponseDto getContent(final Long contentId) {

        ContentGetUserResponseDto contentGetUser = contentHelper.getContentByContentId(contentId);

        contentGetUser.updateImagePath(
                S3Util.makeResponseImageDir(contentGetUser.getMainImagePath()));

        List<ContentDetailImagePathGetUserResponseDto> contentDetailImagePathList =
                contentHelper.getAllContentDetailImagesPathByContentId(contentId);

        for (ContentDetailImagePathGetUserResponseDto dto : contentDetailImagePathList) {
            final String detailImagePath = S3Util.makeResponseImageDir(dto.getDetailImagePath());
            dto.updateDetailImagePath(detailImagePath);
        }

        List<RoundInfoGetUserResponseDto> roundInfoList = roundHelper.getAllRoundsInfoByContentId(
                contentId);

        return ContentDetailResponseDto.of(contentGetUser, contentDetailImagePathList,
                roundInfoList);
    }

    /**
     * 공연 전체조회
     */
    @Transactional(readOnly = true)
    public PageResponseDto<ContentGetUserResponseDto> getAllContents(
            final Long categoryId, final String titleKeyword, final int page,
            final String sortProperty, final String sortDirection) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortProperty);

        PageUtil.checkPageNumber(page);

        Pageable pageable = PageRequest.of(page - 1, 4, sort);

        Page<ContentGetUserResponseDto> pageResponseDto = contentHelper.getAllContentForUser(
                categoryId, titleKeyword, pageable);

        for (ContentGetUserResponseDto dto : pageResponseDto) {
            dto.updateImagePath(S3Util.makeResponseImageDir(dto.getMainImagePath()));
        }

        return PageResponseDto.of(pageResponseDto);
    }

}
