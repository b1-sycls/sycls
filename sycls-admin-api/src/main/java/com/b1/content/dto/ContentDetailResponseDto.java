package com.b1.content.dto;

import com.b1.content.entity.ContentStatus;
import com.b1.round.dto.RoundInfoGetAdminResponseDto;
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
    private final ContentStatus status;
    private final String categoryName;
    private final List<ContentDetailImagePathGetAdminResponseDto> detailImageList;
    private final List<RoundInfoGetAdminResponseDto> roundList;

    public static ContentDetailResponseDto of(final ContentGetAdminResponseDto contentDto,
            final List<ContentDetailImagePathGetAdminResponseDto> detailImageList,
            final List<RoundInfoGetAdminResponseDto> roundList) {
        return ContentDetailResponseDto.builder()
                .contentId(contentDto.getContentId())
                .title(contentDto.getTitle())
                .description(contentDto.getDescription())
                .mainImagePath(contentDto.getMainImagePath())
                .status(contentDto.getStatus())
                .categoryName(contentDto.getCategoryName())
                .detailImageList(detailImageList)
                .roundList(roundList)
                .build();
    }
}
