package com.b1.content.dto;

import com.b1.round.dto.RoundInfoGetUserResponseDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ContentDetailResponseDto {

    private final Long contentId;
    private final String title;
    private final String description;
    private final String mainImagePath;
    private final String categoryName;
    private final List<ContentDetailImagePathGetUserResponseDto> detailImageList;
    private final List<RoundInfoGetUserResponseDto> roundList;

    public static ContentDetailResponseDto of(final ContentGetUserResponseDto contentDto,
            final List<ContentDetailImagePathGetUserResponseDto> detailImageList,
            final List<RoundInfoGetUserResponseDto> roundList) {
        return ContentDetailResponseDto.builder()
                .contentId(contentDto.getContentId())
                .title(contentDto.getTitle())
                .description(contentDto.getDescription())
                .mainImagePath(contentDto.getMainImagePath())
                .categoryName(contentDto.getCategoryName())
                .detailImageList(detailImageList)
                .roundList(roundList)
                .build();
    }
}
