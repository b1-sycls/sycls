package com.b1.round;

import com.b1.round.dto.RoundDetailInfoUserResponseDto;
import com.b1.round.dto.RoundDetailResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "Round Service")
@Service
@RequiredArgsConstructor
@Transactional
public class RoundService {

    private final RoundHelper roundHelper;

    @Transactional(readOnly = true)
    public RoundDetailResponseDto getRound(Long roundId) {

        RoundDetailInfoUserResponseDto responseDto = roundHelper.getRoundDetail(roundId);

        if (responseDto == null) {
            return null;
        }

        return RoundDetailResponseDto.of(responseDto);
    }
}
