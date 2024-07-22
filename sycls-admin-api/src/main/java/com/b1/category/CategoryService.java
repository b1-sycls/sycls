package com.b1.category;

import com.b1.category.dto.CategoryRequestDto;
import com.b1.category.entity.Category;
import com.b1.exception.customexception.CategoryNameDuplicatedException;
import com.b1.exception.errorcode.CategoryErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "Category Service")
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryAdapter categoryAdapter;
    
    public void addCategory(CategoryRequestDto requestDto) {

        String name = requestDto.name();

        if (categoryAdapter.existsByName(name)) {
            log.error("Category Service - Category Name Duplicate Error");
            throw new CategoryNameDuplicatedException(CategoryErrorCode.CATEGORY_NAME_DUPLICATED);
        }

        Category category = Category.addCategory(name);

        categoryAdapter.saveCategory(category);
    }
}
