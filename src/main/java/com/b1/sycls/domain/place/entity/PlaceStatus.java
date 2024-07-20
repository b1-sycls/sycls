package com.b1.sycls.domain.place.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlaceStatus {
    ENABLE("ENABLE"),
    DISABLE("DISABLE"),
    ;

    private final String value;
}
