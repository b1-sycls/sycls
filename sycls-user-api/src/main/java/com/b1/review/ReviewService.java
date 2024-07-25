package com.b1.review;

import com.b1.common.PageResponseDto;
import com.b1.review.dto.ReviewAddRequestDto;
import com.b1.review.dto.ReviewGetResponseDto;
import com.b1.review.dto.ReviewUpdateRequestDto;
import com.b1.review.entity.Review;
import com.b1.review.entity.ReviewStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "Review Service")
@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewHelper reviewHelper;
    //private final ContentHelper contentHelper;

    /**
     * 리뷰 등록
     */
    public void addReview(final Long contentId,
            final ReviewAddRequestDto requestDto/*, User user*/) {
        //Content content = contentHelper.getContent(contentId);
        //Review review = Review.addReview(requestDto.comment(), requestDto.rating(), ReviewStatus.ENABLE, user, content)
        //reviewHelper.saveReview(review);
    }

    /**
     * 리뷰 조회 TODO user&content 완성 후 리팩토링
     */
    @Transactional(readOnly = true)
    public PageResponseDto<ReviewGetResponseDto> getAllReviews(
            final Long contentId,
            final Integer pageNum,
            final Integer pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Direction.DESC, "createdAt");
        Page<ReviewGetResponseDto> reviewList = reviewHelper.getAllReviews(contentId, pageable);
        return PageResponseDto.of(reviewList);
    }

    /**
     * 리뷰 수정
     */
    public Long updateReview(final Long reviewId,
            final ReviewUpdateRequestDto requestDto/*, User user*/) {
        Review review = reviewHelper.getReview(reviewId);
        // TODO User 비교 로직 추가
        // content도 확인해야하나?
        review.updateReview(requestDto.comment(), requestDto.rating());

        return review.getId();
    }

    /**
     * 리뷰 삭제
     */
    public void deleteReview(final Long reviewId) {
        Review review = reviewHelper.getReview(reviewId);
        ReviewStatus.checkDeleted(review.getStatus());
        review.deleteReview();
    }
}
