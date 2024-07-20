package com.b1.sycls.domain.place;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContentAdapter {

    private final ContentRepository contentRepository;

}
