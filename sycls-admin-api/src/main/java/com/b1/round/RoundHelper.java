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

    public Round findById(Long roundId) {
        return roundRepository.findById(roundId)
                .orElseThrow(() -> new RoundNotFoundException(RoundErrorCode.NOT_FOUND_ROUND));
    }

    public List<Round> getAllRoundsByPlaceId(Long placeId, LocalDate startDate) {
        return queryRepository.getAllRoundsByPlaceId(placeId, startDate);
    }

    public void saveRound(Round round) {
        roundRepository.save(round);
    }

    public List<RoundInfoGetAdminResponseDto> getAllRoundsInfoByContentId(Long contentId) {
        return queryRepository.getAllRoundsInfoByContentIdForAdmin(contentId);
    }

    public RoundDetailInfoAdminResponseDto getRoundDetail(Long roundId) {
        return queryRepository.getRoundDetailInfoForAdmin(roundId);
    }

    public Page<RoundSimpleAdminResponseDto> getAllSimpleRoundsForAdmin(
            RoundSearchCondRequest request, Pageable pageable) {
        return queryRepository.getAllSimpleRoundsForAdmin(request.getContentId(),
                request.getStatus(), pageable);
    }
}
