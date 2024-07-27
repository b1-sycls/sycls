package com.b1.cast;

import com.b1.cast.entity.Cast;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Cast Helper")
@Component
@RequiredArgsConstructor
public class CastHelper {

    private final CastRepository castRepository;

    /**
     * 출연진 저장
     */
    public void save(final Cast cast) {
        castRepository.save(cast);
    }
}
