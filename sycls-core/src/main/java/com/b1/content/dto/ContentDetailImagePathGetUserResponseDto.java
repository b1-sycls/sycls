package com.b1.content.dto;

import com.b1.content.entity.ContentDetailImageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContentDetailImagePathGetUserResponseDto {

    private Long contentDetailImageId;

    private String detailImagePath;

    private ContentDetailImageStatus status;

    public void updateDetailImagePath(String detailImagePath) {
        this.detailImagePath = detailImagePath;
    }
}
