package com.b1.sycls.domain.content.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentDetailImageStatus {
    ENABLE("ENABLE"),
    DISABLE("DISABLE"),
    ;

    private final String value;
}
