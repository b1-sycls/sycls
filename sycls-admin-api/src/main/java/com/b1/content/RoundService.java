package com.b1.content;

import com.b1.content.dto.RoundUpdateStatusRequestDto;
import com.b1.content.entity.Round;
import com.b1.exception.customexception.RoundStatusEqualsException;
import com.b1.exception.errorcode.RoundErrorCode;
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

    public void updateStatus(Long roundId, RoundUpdateStatusRequestDto requestDto) {
        Round round = roundHelper.findById(roundId);

        if (round.getStatus() == requestDto.status()) {
            throw new RoundStatusEqualsException(RoundErrorCode.STATUS_EQUALS);
        }

        round.updateStatus(requestDto.status());
    }
}
