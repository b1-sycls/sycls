package com.b1.category;

import com.b1.category.dto.CategoryGetUserResponseDto;
import com.b1.globalresponse.RestApiResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<RestApiResponseDto<List<CategoryGetUserResponseDto>>> getAllCategory() {
        List<CategoryGetUserResponseDto> responseDtoList = categoryService.getAllCategory();
        return ResponseEntity.ok().body(RestApiResponseDto.of("성공", responseDtoList));
    }
}
