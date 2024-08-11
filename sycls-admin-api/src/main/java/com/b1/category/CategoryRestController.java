package com.b1.category;

import com.b1.category.dto.CategoryAddRequestDto;
import com.b1.category.dto.CategoryGetAdminResponseDto;
import com.b1.category.dto.CategoryUpdateRequestDto;
import com.b1.globalresponse.RestApiResponseDto;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    /**
     * 카테고리 추가
     */
    @PostMapping("/categories")
//    @PreAuthorize("hasRole('ADMIN')") // 시큐리티후 추가
    public ResponseEntity<RestApiResponseDto<String>> addCategory(
            @Valid @RequestBody final CategoryAddRequestDto requestDto) {
        categoryService.addCategory(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(RestApiResponseDto.of("등록 성공"));
    }

    /**
     * 카테고리 수정
     */
    @PatchMapping("/categories/{categoryId}")
    public ResponseEntity<RestApiResponseDto<String>> updateCategory(
            @PathVariable final Long categoryId,
            @Valid @RequestBody final CategoryUpdateRequestDto requestDto) {
        categoryService.updateCategory(categoryId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(RestApiResponseDto.of("수정 성공"));
    }

    /**
     * 카테고리 비활성화
     */
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<RestApiResponseDto<String>> disableCategoryStatus(
            @PathVariable final Long categoryId) {
        categoryService.disableCategoryStatus(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(RestApiResponseDto.of("비활성화 성공"));
    }

    /**
     * 카테고리 활성화
     */
    @PatchMapping("/categories/{categoryId}/reactivate")
    public ResponseEntity<RestApiResponseDto<String>> enableCategoryStatus(
            @PathVariable final Long categoryId) {
        categoryService.enableCategoryStatus(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(RestApiResponseDto.of("활성화 성공"));
    }

    /**
     * 카테고리 전체조회
     */
    @GetMapping("/categories")
    public ResponseEntity<RestApiResponseDto<List<CategoryGetAdminResponseDto>>> getAllCategory() {
        List<CategoryGetAdminResponseDto> responseDtoList = categoryService.getAllCategory();
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("조회 성공", responseDtoList));
    }
}
