package com.b1.review;

import com.b1.exception.customexception.ReviewNotFoundException;
import com.b1.exception.errorcode.ReviewErrorCode;
import com.b1.review.dto.ReviewGetResponseDto;
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
     * 리뷰 등록
     */
    public void saveReview(final Review review) {
        reviewRepository.save(review);
    }

    /**
     * 리뷰 조회
     */
    public Page<ReviewGetResponseDto> getAllReviews(final Long contentId, final Pageable pageable) {
        return reviewQueryRepository.getAllReviews(contentId, pageable);
    }

    /**
     * 리뷰 단건 조회
     */
    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(
                () -> {
                    log.error("찾을 수 없는 리뷰 | {}", reviewId);
                    return new ReviewNotFoundException(ReviewErrorCode.NOT_FOUND_REVIEW);
                }
        );
    }
}
