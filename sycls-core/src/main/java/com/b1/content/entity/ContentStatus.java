package com.b1.content.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Round Status")
@Getter
@RequiredArgsConstructor
public enum ContentStatus {
    HIDDEN("HIDDEN"),
    VISIBLE("VISIBLE"),
    DELETED("DELETED"),
    ;

    private final String value;
}
