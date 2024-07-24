package com.b1.review;

import com.b1.review.dto.ReviewGetAllResponseDto;
import com.b1.review.entity.Review;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Review Adapter")
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
    public List<ReviewGetAllResponseDto> getAllReviews(final Long contentId) {
        return reviewQueryRepository.getAllReviews(contentId);
    }
}
