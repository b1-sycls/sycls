package com.b1.review;

import com.b1.review.dto.ReviewGetResponseDto;
import com.b1.review.dto.ReviewSearchCondRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Review Helper")
@Component
@RequiredArgsConstructor
public class ReviewHelper {

    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;

    /**
     * 해당 공연의 리뷰 전체 조회
     */
    public Page<ReviewGetResponseDto> getAllReviewsByContent(final Long contentId,
            final ReviewSearchCondRequestDto requestDto, final Pageable pageable) {
        return reviewQueryRepository.getAllReviewsByContent(
                contentId,
                requestDto.getEmail(),
                requestDto.getNickName(),
                requestDto.getReviewStatus(),
                pageable
        );
    }

    /**
     * 리뷰 상세 조회
     */
    public ReviewGetResponseDto getReview(Long reviewId) {
        return reviewQueryRepository.getReview(reviewId);
    }
}
