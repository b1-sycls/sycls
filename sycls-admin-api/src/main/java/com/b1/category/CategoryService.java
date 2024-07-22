package com.b1.category;

import com.b1.category.dto.CategoryRequestDto;
import com.b1.category.dto.CategoryUpdateRequestDto;
import com.b1.category.entity.Category;
import com.b1.category.entity.CategoryStatus;
import com.b1.content.ContentAdapter;
import com.b1.exception.customexception.CategoryInUseException;
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
    private final ContentAdapter contentAdapter;

    public void addCategory(CategoryRequestDto requestDto) {
        String name = requestDto.name();

        checkCategoryDuplicatedName(name);

        Category category = Category.addCategory(name);

        categoryAdapter.saveCategory(category);
    }

    public void updateCategory(Long categoryId, CategoryUpdateRequestDto requestDto) {
        String name = requestDto.name();

        checkCategoryDuplicatedName(name);

        Category category = categoryAdapter.findById(categoryId);

        category.update(name);
    }

    public void deleteCategory(Long categoryId) {
        Category category = categoryAdapter.findById(categoryId);

        CategoryStatus.checkDisable(category.getStatus());

        if (contentAdapter.existsByCategoryId(categoryId)) {
            log.error("공연에서 사용하고 있는 카테고리 | request : {}", categoryId);
            throw new CategoryInUseException(CategoryErrorCode.CATEGORY_IN_USE);
        }

        category.disableStatus();
    }

    public void reactivateCategory(Long categoryId) {
        Category category = categoryAdapter.findById(categoryId);

        CategoryStatus.checkEnable(category.getStatus());

        category.enableStatus();
    }

    private void checkCategoryDuplicatedName(String name) {
        if (categoryAdapter.existsByName(name)) {
            log.error("중복된 카테고리 이름 | name : {}", name);
            throw new CategoryNameDuplicatedException(CategoryErrorCode.CATEGORY_NAME_DUPLICATED);
        }
    }
}
