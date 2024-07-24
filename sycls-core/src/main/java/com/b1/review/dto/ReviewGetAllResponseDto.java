package com.b1.review.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class ReviewGetAllResponseDto {

    private String email;
    private String comment;
    private Integer rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ReviewGetAllResponseDto of(String email, String comment, Integer rating,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        return ReviewGetAllResponseDto.builder()
                .email(email)
                .comment(comment)
                .rating(rating)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

}
