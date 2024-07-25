package com.b1.content.dto;

import com.b1.content.entity.ContentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContentGetUserResponseDto {

    private Long contentId;

    private String title;

    private String description;

    private String mainImagePath;

    private ContentStatus status;

    private String categoryName;

    public void updateImagePath(String mainImagePath) {
        this.mainImagePath = mainImagePath;
    }
}
