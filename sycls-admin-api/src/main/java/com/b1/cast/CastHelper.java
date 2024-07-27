package com.b1.cast;

import com.b1.cast.entity.Cast;
import com.b1.exception.customexception.CastNotFoundException;
import com.b1.exception.errorcode.CastErrorCode;
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

    /**
     * 출연진 entity 반환
     */
    public Cast getCast(final Long castId) {
        return castRepository.findById(castId)
                .orElseThrow(() -> {
                    log.error("출연진을 찾을 수 없음 | castId : {}", castId);
                    return new CastNotFoundException(CastErrorCode.CAST_NOT_FOUND);
                });
    }
}
