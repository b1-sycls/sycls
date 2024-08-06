package com.b1.review;

import com.b1.common.PageResponseDto;
import com.b1.content.ContentHelper;
import com.b1.review.dto.ReviewGetAdminResponseDto;
import com.b1.review.dto.ReviewGetUserResponseDto;
import com.b1.review.dto.ReviewSearchCondRequestDto;
import com.b1.review.entity.Review;
import com.b1.review.entity.ReviewStatus;
import com.b1.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "Review Service")
@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewHelper reviewHelper;
    private final ContentHelper contentHelper;

    /**
     * 해당 공연의 리뷰 전체 조회
     */
    @Transactional(readOnly = true)
    public PageResponseDto<ReviewGetAdminResponseDto> getAllReviewsByContent(
            final Long contentId,
            final ReviewSearchCondRequestDto requestDto
    ) {
        contentHelper.checkContentById(contentId);

        Sort.Direction direction = requestDto.getIsDesc() ? Direction.DESC : Direction.ASC;
        PageUtil.checkPageNumber(requestDto.getPageNum());
        Pageable pageable = PageRequest.of(
                requestDto.getPageNum() - 1,
                requestDto.getPageSize(),
                direction,
                requestDto.getOrderBy()
        );

        Page<ReviewGetAdminResponseDto> reviewPage = reviewHelper.getAllReviewsByContent(
                contentId,
                requestDto,
                pageable
        );

        return PageResponseDto.of(reviewPage);
    }

    /**
     * 리뷰 상세 조회
     */
    @Transactional(readOnly = true)
    public ReviewGetUserResponseDto getReview(final Long reviewId) {
        return reviewHelper.getReview(reviewId);
    }

    /**
     * 리뷰 삭제
     */
    public void deleteReview(final Long reviewId) {
        Review review = reviewHelper.findReview(reviewId);
        ReviewStatus.checkDeleted(review.getStatus());
        review.deleteReview();
    }
}
