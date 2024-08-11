package com.b1.review;

import com.b1.exception.customexception.ReviewNotFoundException;
import com.b1.exception.errorcode.ReviewErrorCode;
import com.b1.review.dto.ReviewGetAdminResponseDto;
import com.b1.review.dto.ReviewGetUserResponseDto;
import com.b1.review.dto.ReviewSearchCondRequestDto;
import com.b1.review.entity.Review;
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
    public Page<ReviewGetAdminResponseDto> getAllReviewsByContent(
            final Long contentId,
            final ReviewSearchCondRequestDto requestDto,
            final Pageable pageable
    ) {
        return reviewQueryRepository.getAllReviewsByContent(
                contentId,
                requestDto.getRating(),
                requestDto.getNickName(),
                requestDto.getReviewStatus(),
                pageable
        );
    }

    /**
     * 리뷰 상세 조회
     */
    public ReviewGetUserResponseDto getReview(final Long reviewId) {
        return reviewQueryRepository.getReview(reviewId);
    }

    /**
     * 리뷰 findById
     */
    public Review findReview(final Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(
                () -> {
                    log.error("찾을 수 없는 리뷰 | {}", reviewId);
                    return new ReviewNotFoundException(ReviewErrorCode.NOT_FOUND_REVIEW);
                }
        );
    }
}
