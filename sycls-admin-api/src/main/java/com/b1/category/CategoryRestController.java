package com.b1.category;

import com.b1.category.dto.CategoryGetResponseDto;
import com.b1.category.dto.CategoryRequestDto;
import com.b1.category.dto.CategoryUpdateRequestDto;
import com.b1.globalresponse.RestApiResponseDto;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "Category Rest Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class CategoryRestController {

    private final CategoryService categoryService;

    @PostMapping("/categories")
//    @PreAuthorize("hasRole('ADMIN')") // 시큐리티후 추가
    public ResponseEntity<RestApiResponseDto> addCategory(
            @Valid @RequestBody final CategoryRequestDto requestDto) {
        categoryService.addCategory(requestDto);
        return ResponseEntity.ok().body(RestApiResponseDto.of("성공"));
    }

    @PatchMapping("/categories/{categoryId}")
    public ResponseEntity<RestApiResponseDto> updateCategory(@PathVariable Long categoryId,
            @Valid @RequestBody final CategoryUpdateRequestDto requestDto) {
        categoryService.updateCategory(categoryId, requestDto);
        return ResponseEntity.ok().body(RestApiResponseDto.of("성공"));
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<RestApiResponseDto> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().body(RestApiResponseDto.of("성공"));
    }

    @PatchMapping("/categories/{categoryId}/reactivate")
    public ResponseEntity<RestApiResponseDto> reactivateCategory(@PathVariable Long categoryId) {
        categoryService.reactivateCategory(categoryId);
        return ResponseEntity.ok().body(RestApiResponseDto.of("성공"));
    }

    @GetMapping("/v1/categories")
    public ResponseEntity<RestApiResponseDto<List<CategoryGetResponseDto>>> getAllCategory() {
        List<CategoryGetResponseDto> responseDtoList = categoryService.getAllCategory();
        return ResponseEntity.ok().body(RestApiResponseDto.of("성공", responseDtoList));
    }
}
