package com.b1.category;

import com.b1.category.dto.CategoryRequestDto;
import com.b1.globalresponse.RestApiResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
}
