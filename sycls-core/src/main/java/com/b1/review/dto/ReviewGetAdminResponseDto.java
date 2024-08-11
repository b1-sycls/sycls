package com.b1.review.dto;

import com.b1.review.entity.ReviewStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewGetAdminResponseDto {

    private Long id;
    private String nickName;
    private String comment;
    private Integer rating;
    private ReviewStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}