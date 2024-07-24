package com.b1.content.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentStatus {
    HIDDEN("HIDDEN"),
    VISIBLE("VISIBLE"),
    DELETED("DELETED"),
    ;

    private final String value;
}
