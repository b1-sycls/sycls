package com.b1.round;

import com.b1.exception.customexception.RoundNotFoundException;
import com.b1.exception.errorcode.RoundErrorCode;
import com.b1.round.dto.ContentAndRoundGetResponseDto;
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
    private final RoundQueryRepository roundQueryRepository;

    /**
     * 공연 단일 조회에 필요한 회차 조회
     */
    public List<RoundInfoGetUserResponseDto> getAllRoundsInfoByContentId(final Long contentId) {
        return roundQueryRepository.getAllRoundsInfoByContentIdForUser(contentId);
    }

    /**
     * 공연회차 정보 조회
     *
     * @throws RoundNotFoundException 찾을 수 없는 공연입니다.
     */
    public Round getRound(final Long roundId) {
        Round round = roundRepository.findById(roundId)
                .orElseThrow(() -> {
                    log.error("찾을 수 없는 공연 | request : {}", roundId);
                    return new RoundNotFoundException(RoundErrorCode.NOT_FOUND_ROUND);
                });
        RoundStatus.unCheckAvailable(round.getStatus());
        return round;
    }

    /**
     * 회차 단일 상세 조회
     */
    public RoundDetailInfoUserResponseDto getRoundDetail(final Long roundId) {
        return roundQueryRepository.getRoundDetailInfoForUser(roundId);
    }

    /**
     * 회차 목록 조회
     */
    public Page<RoundSimpleUserResponseDto> getAllSimpleRoundsForUser(final Long contentId,
            final Pageable pageable) {
        return roundQueryRepository.getAllSimpleRoundsForUser(contentId, pageable);
    }

    /**
     * 회차 단일 간단 조회
     */
    public ContentAndRoundGetResponseDto getRoundSimple(final Long roundId) {
        return roundQueryRepository.getRoundSimple(roundId);
    }
}
