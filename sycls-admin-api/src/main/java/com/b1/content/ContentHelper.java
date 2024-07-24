package com.b1.content;

import com.b1.content.entity.Content;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Content Helper")
@Component
@RequiredArgsConstructor
public class ContentHelper {

    private final ContentRepository contentRepository;

    public boolean existsByCategoryId(Long categoryId) {
        return contentRepository.existsByCategoryId(categoryId);
    }

    public void saveContent(Content content) {
        contentRepository.save(content);
    }
}
