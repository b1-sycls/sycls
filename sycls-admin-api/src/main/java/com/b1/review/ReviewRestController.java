package com.b1.review;

import com.b1.common.PageResponseDto;
import com.b1.globalresponse.RestApiResponseDto;
import com.b1.review.dto.ReviewGetResponseDto;
import com.b1.review.dto.ReviewSearchCondRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "Review Rest Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ReviewRestController {

    private final ReviewService reviewService;

    /**
     * 해당 공연의 리뷰 전체 조회
     */
    @GetMapping("/contents/{contentId}/reviews")
    public ResponseEntity<RestApiResponseDto<PageResponseDto<ReviewGetResponseDto>>> getAllReviewsByContent(
            @PathVariable final Long contentId,
            @ModelAttribute final ReviewSearchCondRequestDto requestDto
    ) {
        PageResponseDto<ReviewGetResponseDto> response = reviewService.getAllReviewsByContent(
                contentId,
                requestDto
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("조회되었습니다.", response));
    }

    /**
     * 리뷰 상세 조회
     */
    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<RestApiResponseDto<ReviewGetResponseDto>> getReview(
            @PathVariable final Long reviewId
    ) {
        ReviewGetResponseDto response = reviewService.getReview(reviewId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("조회되었습니다.", response));
    }

}
