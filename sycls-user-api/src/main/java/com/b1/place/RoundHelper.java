package com.b1.place;

import com.b1.content.RoundRepository;
import com.b1.content.entity.Round;
import com.b1.content.entity.RoundStatus;
import com.b1.exception.customexception.RoundNotFoundException;
import com.b1.exception.errorcode.RoundErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Round Helper")
@Component
@RequiredArgsConstructor
public class RoundHelper {

    private final RoundRepository roundRepository;

    /**
     * 공연회차 정보 조회
     *
     * @throws RoundNotFoundException 찾을 수 없는 공연입니다.
     */
    public Round getRound(Long roundId) {
        Round round = roundRepository.findById(roundId)
                .orElseThrow(() -> {
                    log.error("찾을 수 없는 공연 | request : {}", roundId);
                    return new RoundNotFoundException(RoundErrorCode.NOT_FOUND_ROUND);
                });
        RoundStatus.checkAvailable(round.getStatus());
        return round;
    }
}
