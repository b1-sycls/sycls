package com.b1.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewAdapter {

    private final ReviewRepository reviewRepository;
}
