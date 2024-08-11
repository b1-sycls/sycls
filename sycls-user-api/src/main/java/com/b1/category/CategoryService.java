package com.b1.category;

import com.b1.category.dto.CategoryGetUserResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "Category Service")
@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryHelper categoryHelper;

    /**
     * 카테고리 전체 조회
     */
    @Transactional(readOnly = true)
    public List<CategoryGetUserResponseDto> getAllCategory() {
        return categoryHelper.getAllCategoryOrderByNameAsc();
    }
}
