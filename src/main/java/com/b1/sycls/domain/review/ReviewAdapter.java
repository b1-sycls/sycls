package com.b1.sycls.domain.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewAdapter {

    private final ReviewRepository reviewRepository;
}
