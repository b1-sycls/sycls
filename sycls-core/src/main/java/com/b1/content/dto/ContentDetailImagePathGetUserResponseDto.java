package com.b1.content.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContentDetailImagePathGetUserResponseDto {

    private final Long contentDetailImageId;

    private final String detailImagePath;
}
