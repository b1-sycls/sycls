package com.b1.review;

import com.b1.review.dto.ReviewAddRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
