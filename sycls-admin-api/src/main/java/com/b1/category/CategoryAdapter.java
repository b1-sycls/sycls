package com.b1.category;

import com.b1.category.entity.Category;
import com.b1.exception.customexception.CateGoryNotFoundException;
import com.b1.exception.errorcode.CategoryErrorCode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryAdapter {

    private static final Logger log = LoggerFactory.getLogger(CategoryAdapter.class);
    private final CategoryRepository categoryRepository;

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.error("카테고리를 찾지 못함 | request : {}", categoryId);
                    return new CateGoryNotFoundException(
                            CategoryErrorCode.CATEGORY_NOT_FOUND_EXCEPTION);
                });
    }
}
