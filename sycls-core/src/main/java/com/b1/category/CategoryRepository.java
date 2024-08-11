package com.b1.category;

import com.b1.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 카테고리 이름 중복 확인
     */
    boolean existsByName(final String name);
}
