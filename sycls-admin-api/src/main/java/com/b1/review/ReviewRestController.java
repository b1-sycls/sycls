package com.b1.review;

import com.b1.common.PageResponseDto;
import com.b1.globalresponse.RestApiResponseDto;
import com.b1.review.dto.ReviewGetAdminResponseDto;
import com.b1.review.dto.ReviewGetUserResponseDto;
import com.b1.review.dto.ReviewSearchCondRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ReviewRestController {

    private final ReviewService reviewService;

    /**
     * 해당 공연의 리뷰 전체 조회
     */
    @GetMapping("/contents/{contentId}/reviews")
    public ResponseEntity<RestApiResponseDto<PageResponseDto<ReviewGetAdminResponseDto>>> getAllReviewsByContent(
            @PathVariable final Long contentId,
            @ModelAttribute final ReviewSearchCondRequestDto requestDto
    ) {
        PageResponseDto<ReviewGetAdminResponseDto> response = reviewService.getAllReviewsByContent(
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
    public ResponseEntity<RestApiResponseDto<ReviewGetUserResponseDto>> getReview(
            @PathVariable final Long reviewId
    ) {
        ReviewGetUserResponseDto response = reviewService.getReview(reviewId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("조회되었습니다.", response));
    }

    /**
     * 리뷰 삭제
     */
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<RestApiResponseDto<String>> deleteReview(
            @PathVariable final Long reviewId
    ) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("삭제되었습니다."));
    }
}
