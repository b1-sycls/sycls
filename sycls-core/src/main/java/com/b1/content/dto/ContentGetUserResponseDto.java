package com.b1.content.dto;

import com.b1.content.entity.ContentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContentGetUserResponseDto {

    private final Long contentId;

    private final String title;

    private final String description;

    private final String mainImagePath;

    private final ContentStatus status;

    private final String categoryName;
}
