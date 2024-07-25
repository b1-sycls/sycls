package com.b1.round;

import com.b1.exception.customexception.RoundNotFoundException;
import com.b1.exception.errorcode.RoundErrorCode;
import com.b1.round.dto.RoundDetailInfoAdminResponseDto;
import com.b1.round.dto.RoundInfoGetAdminResponseDto;
import com.b1.round.dto.RoundSearchCondRequest;
import com.b1.round.dto.RoundSimpleAdminResponseDto;
import com.b1.round.entity.Round;
import java.time.LocalDate;
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

    /**
     * 회차 Entity 반환
     */
    public Round findById(final Long roundId) {
        return roundRepository.findById(roundId)
                .orElseThrow(() -> new RoundNotFoundException(RoundErrorCode.NOT_FOUND_ROUND));
    }

    /**
     * 회차 Entity List 반환
     */
    public List<Round> getAllRoundsByPlaceId(final Long placeId, final LocalDate startDate) {
        return queryRepository.getAllRoundsByPlaceId(placeId, startDate);
    }

    /**
     * 회차 저장
     */
    public void saveRound(final Round round) {
        roundRepository.save(round);
    }

    /**
     * 공연 단일 조회 기능에 들어가는 회차 정보
     */
    public List<RoundInfoGetAdminResponseDto> getAllRoundsInfoByContentId(final Long contentId) {
        return queryRepository.getAllRoundsInfoByContentIdForAdmin(contentId);
    }

    /**
     * 회차 단일 상세 조회 TODO 필드 추가 가능성 농후
     */
    public RoundDetailInfoAdminResponseDto getRoundDetail(final Long roundId) {
        return queryRepository.getRoundDetailInfoForAdmin(roundId);
    }

    /**
     * 회차 목록 정보 반환
     */
    public Page<RoundSimpleAdminResponseDto> getAllSimpleRoundsForAdmin(
            final RoundSearchCondRequest request, final Pageable pageable) {
        return queryRepository.getAllSimpleRoundsForAdmin(request.getContentId(),
                request.getStatus(), pageable);
    }
}
