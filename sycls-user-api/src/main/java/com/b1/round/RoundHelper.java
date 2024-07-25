package com.b1.round;

import com.b1.exception.customexception.RoundNotFoundException;
import com.b1.exception.errorcode.RoundErrorCode;
import com.b1.round.dto.RoundDetailInfoUserResponseDto;
import com.b1.round.dto.RoundInfoGetUserResponseDto;
import com.b1.round.dto.RoundSimpleUserResponseDto;
import com.b1.round.entity.Round;
import com.b1.round.entity.RoundStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Round Helper")
@Component
@RequiredArgsConstructor
public class RoundHelper {

    private final RoundRepository roundRepository;
    private final RoundQueryRepository queryRepository;

    public List<RoundInfoGetUserResponseDto> getAllRoundsInfoByContentId(Long contentId) {
        return queryRepository.getAllRoundsInfoByContentIdForUser(contentId);
    }

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

    public RoundDetailInfoUserResponseDto getRoundDetail(Long roundId) {
        return queryRepository.getRoundDetailInfoForUser(roundId);
    }

    public Page<RoundSimpleUserResponseDto> getAllSimpleRoundsForUser(Long contentId,
            Pageable pageable) {
        return queryRepository.getAllSimpleRoundsForUser(contentId, pageable);
    }
}
