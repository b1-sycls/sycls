package com.b1.review;

import com.b1.common.PageResponseDto;
import com.b1.globalresponse.RestApiResponseDto;
import com.b1.review.dto.ReviewAddRequestDto;
import com.b1.review.dto.ReviewGetUserResponseDto;
import com.b1.review.dto.ReviewUpdateRequestDto;
import com.b1.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ReviewRestController {

    private final ReviewService reviewService;

    /**
     * 리뷰 등록
     */
    @PostMapping("/contents/{contentId}/reviews")
    public ResponseEntity<RestApiResponseDto<String>> addReview(
            @PathVariable final Long contentId,
            @Valid @RequestBody final ReviewAddRequestDto requestDto,
            @AuthenticationPrincipal final UserDetailsImpl userDetails
    ) {
        reviewService.addReview(contentId, requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("등록되었습니다."));
    }

    /**
     * 리뷰 전체 조회
     */
    @GetMapping("/contents/{contentId}/reviews")
    public ResponseEntity<RestApiResponseDto<PageResponseDto<ReviewGetUserResponseDto>>> getAllReviews(
            @PathVariable final Long contentId,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") final Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "4") final Integer pageSize
    ) {
        PageResponseDto<ReviewGetUserResponseDto> response =
                reviewService.getAllReviews(
                        contentId,
                        pageNum,
                        pageSize
                );
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("조회되었습니다.", response));
    }

    /**
     * 리뷰 수정
     */
    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<RestApiResponseDto<Long>> updateReview(
            @PathVariable final Long reviewId,
            @RequestBody final ReviewUpdateRequestDto requestDto,
            @AuthenticationPrincipal final UserDetailsImpl userDetails
    ) {
        Long response = reviewService.updateReview(reviewId, requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("수정되었습니다.", response));
    }

    /**
     * 리뷰 삭제
     */
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<RestApiResponseDto<String>> deleteReview(
            @PathVariable final Long reviewId,
            @AuthenticationPrincipal final UserDetailsImpl userDetails
    ) {
        reviewService.deleteReview(reviewId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("삭제되었습니다,"));
    }

}
