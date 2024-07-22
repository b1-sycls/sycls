package com.b1.category.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryStatus {

    ENABLE("ENABLE"),
    DISABLE("DISABLE"),
    ;

    private final String value;

}
