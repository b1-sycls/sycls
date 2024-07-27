package com.b1.cast.entity.dto;

import com.b1.cast.entity.CastStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CastGetUserResponseDto {

    private Long castId;

    private String name;

    private String imagePath;

    private CastStatus status;

    public void updateImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}