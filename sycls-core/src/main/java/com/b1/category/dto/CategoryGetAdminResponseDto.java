package com.b1.category.dto;

import com.b1.category.entity.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryGetAdminResponseDto {

    private final Long id;

    private final String name;

    private final CategoryStatus status;

}
