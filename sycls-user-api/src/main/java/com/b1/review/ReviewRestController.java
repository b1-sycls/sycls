package com.b1.review;

import com.b1.globalresponse.RestApiResponseDto;
import com.b1.review.dto.ReviewAddRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "Review Rest Controller")
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
            @Valid @RequestBody final ReviewAddRequestDto requestDto
            //, @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        reviewService.addReview(contentId, requestDto/*, userDetails.getUser()*/);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("등록되었습니다."));
    }

}
