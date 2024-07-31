package com.b1.round;

import com.b1.exception.customexception.PlaceCannotUpdateException;
import com.b1.exception.customexception.RoundNotFoundException;
import com.b1.exception.errorcode.PlaceErrorCode;
import com.b1.exception.errorcode.RoundErrorCode;
import com.b1.round.dto.RoundDetailInfoAdminResponseDto;
import com.b1.round.dto.RoundInfoGetAdminResponseDto;
import com.b1.round.dto.RoundSimpleAdminResponseDto;
import com.b1.round.entity.Round;
import com.b1.round.entity.RoundStatus;
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
    private final RoundQueryRepository roundQueryRepository;

    /**
     * 회차 Entity 반환
     */
    public Round findById(final Long roundId) {
        return roundRepository.findById(roundId)
                .orElseThrow(() -> {
                    log.error("회차를 찾지 못함 | roundId : {}", roundId);
                    return new RoundNotFoundException(RoundErrorCode.NOT_FOUND_ROUND);
                });
    }

    /**
     * 회차 Entity List 반환
     */
    public List<Round> getAllRoundsByPlaceId(final Long placeId, final LocalDate startDate) {
        return roundQueryRepository.getAllRoundsByPlaceId(placeId, startDate);
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
        return roundQueryRepository.getAllRoundsInfoByContentIdForAdmin(contentId);
    }

    /**
     * 회차 단일 상세 조회 TODO 필드 추가 가능성 농후
     */
    public RoundDetailInfoAdminResponseDto getRoundDetail(final Long roundId) {
        return roundQueryRepository.getRoundDetailInfoForAdmin(roundId);
    }

    /**
     * 회차 목록 정보 반환
     */
    public Page<RoundSimpleAdminResponseDto> getAllSimpleRoundsForAdmin(final Long contentId,
            final RoundStatus status, final Pageable pageable) {
        return roundQueryRepository.getAllSimpleRoundsForAdmin(contentId, status, pageable);
    }

    /**
     * 해당 공연장을 사용하고 회차상태가 AVAILABLE인 공연장 존재 여부 확인
     */
    public void existsRoundByPlaceIdAndStatus(final Long placeId) {
        if (roundRepository.existsByPlaceIdAndStatus(placeId, RoundStatus.AVAILABLE)) {
            log.error("예매 중인 공연이 존재해 공연장을 수정할 수 없습니다. | {}", placeId);
            throw new PlaceCannotUpdateException(PlaceErrorCode.CANNOT_UPDATE_PLACE);
        }
    }
}
