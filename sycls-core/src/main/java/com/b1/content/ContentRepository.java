package com.b1.content;

import com.b1.content.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {

    /**
     * 카테고리가 있는지 확인
     */
    boolean existsByCategoryId(final Long categoryId);
}
