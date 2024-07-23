package com.b1.content;

import com.b1.content.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {

    boolean existsByCategoryId(Long categoryId);
}
