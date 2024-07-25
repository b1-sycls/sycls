package com.b1.content.dto;

import com.b1.content.entity.ContentDetailImageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContentDetailImagePathGetUserResponseDto {

    private final Long contentDetailImageId;

    private final String detailImagePath;

    private final ContentDetailImageStatus status;
}
