package com.b1.place;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContentAdapter {

    private final ContentRepository contentRepository;

}
