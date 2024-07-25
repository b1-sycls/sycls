package com.b1.category;

import com.b1.category.dto.CategoryGetAdminResponseDto;
import com.b1.category.entity.Category;
import com.b1.exception.customexception.CategoryNotFoundException;
import com.b1.exception.errorcode.CategoryErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Category Helper")
@Component
@RequiredArgsConstructor
public class CategoryHelper {

    private final CategoryRepository categoryRepository;
    private final CategoryQueryRepository categoryQueryRepository;

    /**
     * 카테고리 저장
     */
    public void saveCategory(final Category category) {
        categoryRepository.save(category);
    }

    /**
     * 카테고리 이름 중복 확인
     */
    public boolean existsByName(final String name) {
        return categoryRepository.existsByName(name);
    }

    /**
     * 카테고리 아이디로 카테고리 반환
     */
    public Category findById(final Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.error("카테고리를 찾지 못함 | request : {}", categoryId);
                    return new CategoryNotFoundException(
                            CategoryErrorCode.CATEGORY_NOT_FOUND);
                });
    }

    /**
     * 카테고리 전체 조회
     */
    public List<CategoryGetAdminResponseDto> getAllCategoryOrderByNameAsc() {
        return categoryQueryRepository.getAllOrderByNameAscForAdmin();
    }
}
