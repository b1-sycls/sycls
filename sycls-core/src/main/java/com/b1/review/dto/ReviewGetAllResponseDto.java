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

    public static ReviewGetAllResponseDto of(final String email, final String comment,
            final Integer rating, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        return ReviewGetAllResponseDto.builder()
                .email(email)
                .comment(comment)
                .rating(rating)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

}
