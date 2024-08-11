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

    /**
     * 카테고리 등록
     */
    public void addCategory(final CategoryAddRequestDto requestDto) {
        String name = requestDto.name();

        checkCategoryDuplicatedName(name);

        Category category = Category.addCategory(name);

        categoryHelper.saveCategory(category);
    }

    /**
     * 카테고리 수정 TODO 상태변경 기능 추가 생각중
     */
    public void updateCategory(final Long categoryId, final CategoryUpdateRequestDto requestDto) {
        String name = requestDto.name();

        checkCategoryDuplicatedName(name);

        Category category = categoryHelper.findById(categoryId);

        category.update(name);
    }

    /**
     * 카테고리 비활성화
     */
    public void disableCategoryStatus(final Long categoryId) {
        Category category = categoryHelper.findById(categoryId);

        CategoryStatus.checkDisable(category.getStatus());

        if (contentHelper.existsByCategoryId(categoryId)) {
            log.error("공연에서 사용하고 있는 카테고리 | request : {}", categoryId);
            throw new CategoryInUseException(CategoryErrorCode.CATEGORY_IN_USE);
        }

        category.disableStatus();
    }

    /**
     * 카테고리 활성화
     */
    public void enableCategoryStatus(final Long categoryId) {
        Category category = categoryHelper.findById(categoryId);

        CategoryStatus.checkEnable(category.getStatus());

        category.enableStatus();
    }

    /**
     * 카테고리 전체조회
     */
    @Transactional(readOnly = true)
    public List<CategoryGetAdminResponseDto> getAllCategory() {
        return categoryHelper.getAllCategoryOrderByNameAsc();
    }

    /**
     * 카테고리 이름 중복 확인
     */
    private void checkCategoryDuplicatedName(final String name) {
        if (categoryHelper.existsByName(name)) {
            log.error("중복된 카테고리 이름 | name : {}", name);
            throw new CategoryNameDuplicatedException(CategoryErrorCode.CATEGORY_NAME_DUPLICATED);
        }
    }
}
