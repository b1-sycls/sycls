package com.b1.content;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContentAdapter {

    private final ContentRepository contentRepository;

    public boolean existsByCategoryId(Long categoryId) {
        return contentRepository.existsByCategoryId(categoryId);
    }
}
