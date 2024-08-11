package com.b1.cast;

import com.b1.cast.entity.dto.CastGetUserResponseDto;
import com.b1.s3.S3Util;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "Cast Service")
@Service
@RequiredArgsConstructor
@Transactional
public class CastService {

    private final CastHelper castHelper;

    /**
     * 출연진 조회 기능
     */
    @Transactional(readOnly = true)
    public List<CastGetUserResponseDto> getAllCastsByRoundId(final Long roundId) {

        List<CastGetUserResponseDto> responseDto = castHelper.getAllCastsByRoundId(roundId);

        for (CastGetUserResponseDto dto : responseDto) {
            dto.updateImagePath(S3Util.makeResponseImageDir(dto.getImagePath()));
        }

        return responseDto;
    }

}
