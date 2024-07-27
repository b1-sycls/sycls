package com.b1.cast;

import com.b1.cast.entity.CastQueryRepository;
import com.b1.cast.entity.dto.CastGetUserResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Cast Helper")
@Component
@RequiredArgsConstructor
public class CastHelper {

    private final CastQueryRepository castQueryRepository;

    /**
     * 출연진 조회
     */
    public List<CastGetUserResponseDto> getAllCastsByRoundId(final Long roundId) {
        return castQueryRepository.getAllCastsByRoundIdForUser(roundId);
    }
}
