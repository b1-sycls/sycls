package com.b1.content.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContentGetUserResponseDto {

    private final Long contentId;

    private final String title;

    private final String description;

    private final String mainImagePath;

    private final String categoryName;
}
