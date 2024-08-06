package com.b1.review.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewGetAllResponseDto {

    private Long id;
    private String email;
    private String comment;
    private Integer rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ReviewGetAllResponseDto of(final Long id, final String email,
            final String comment, final Integer rating, final LocalDateTime createdAt,
            final LocalDateTime updatedAt) {
        return ReviewGetAllResponseDto.builder()
                .id(id)
                .email(email)
                .comment(comment)
                .rating(rating)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

}
