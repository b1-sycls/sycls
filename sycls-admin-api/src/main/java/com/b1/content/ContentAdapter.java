package com.b1.content;

import com.b1.content.entity.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContentAdapter {

    private final ContentRepository contentRepository;

    public boolean existsByCategoryId(Long categoryId) {
        return contentRepository.existsByCategoryId(categoryId);
    }

    public void saveContent(Content content) {
        contentRepository.save(content);
    }
}
