package com.b1.category;

import com.b1.category.dto.CategoryAddRequestDto;
import com.b1.category.dto.CategoryGetAdminResponseDto;
import com.b1.category.dto.CategoryUpdateRequestDto;
import com.b1.category.entity.Category;
import com.b1.category.entity.CategoryStatus;
import com.b1.content.ContentHelper;
import com.b1.exception.customexception.CategoryInUseException;
import com.b1.exception.customexception.CategoryNameDuplicatedException;
import com.b1.exception.errorcode.CategoryErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "Category Service")
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryHelper categoryHelper;
    private final ContentHelper contentHelper;

    public void addCategory(CategoryAddRequestDto requestDto) {
        String name = requestDto.name();

        checkCategoryDuplicatedName(name);

        Category category = Category.addCategory(name);

        categoryHelper.saveCategory(category);
    }

    // TODO 업데이트에 상태변화 api 합치기
    public void updateCategory(Long categoryId, CategoryUpdateRequestDto requestDto) {
        String name = requestDto.name();

        checkCategoryDuplicatedName(name);

        Category category = categoryHelper.findById(categoryId);

        category.update(name);
    }

    public void disableCategoryStatus(Long categoryId) {
        Category category = categoryHelper.findById(categoryId);

        CategoryStatus.checkDisable(category.getStatus());

        if (contentHelper.existsByCategoryId(categoryId)) {
            log.error("공연에서 사용하고 있는 카테고리 | request : {}", categoryId);
            throw new CategoryInUseException(CategoryErrorCode.CATEGORY_IN_USE);
        }

        category.disableStatus();
    }

    public void enableCategoryStatus(Long categoryId) {
        Category category = categoryHelper.findById(categoryId);

        CategoryStatus.checkEnable(category.getStatus());

        category.enableStatus();
    }

    @Transactional(readOnly = true)
    public List<CategoryGetAdminResponseDto> getAllCategory() {
        return categoryHelper.getAllCategoryOrderByNameAsc();
    }

    private void checkCategoryDuplicatedName(String name) {
        if (categoryHelper.existsByName(name)) {
            log.error("중복된 카테고리 이름 | name : {}", name);
            throw new CategoryNameDuplicatedException(CategoryErrorCode.CATEGORY_NAME_DUPLICATED);
        }
    }
}
