package com.b1.content.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record ContentUpdateRequestDto(

        @NotNull(message = "카테고리 고유번호가 누락 되었습니다.")
        Long categoryId,

        @NotBlank(message = "공연 제목이 누락 되었습니다.")
        @Size(max = 30, message = "공연 제목은 30자 이하여야 합니다.")
        String title,

        @Size(max = 50, message = "공연 설명은 50자 이하여야 합니다.")
        String description,

        @Size(max = 300, message = "이미지 주소 길이는 300자 이하여야 합니다.")
        String oldMainImagePath,

        List<@Size(max = 300, message = "이미지 주소 길이는 300자 이하여야 합니다.") String> detailImagePaths
) {

}
