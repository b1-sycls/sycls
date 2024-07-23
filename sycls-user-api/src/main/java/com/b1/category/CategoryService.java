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

    private final CategoryAdapter categoryAdapter;

    @Transactional(readOnly = true)
    public List<CategoryGetUserResponseDto> getAllCategory() {
        return categoryAdapter.getAllCategoryOrderByNameAsc();
    }
}
