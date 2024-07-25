package com.b1.round;

import com.b1.exception.customexception.RoundNotFoundException;
import com.b1.exception.errorcode.RoundErrorCode;
import com.b1.round.dto.RoundDetailInfoAdminResponseDto;
import com.b1.round.dto.RoundInfoGetAdminResponseDto;
import com.b1.round.entity.Round;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public RoundDetailInfoAdminResponseDto getRound(Long roundId) {
        return queryRepository.getRoundByRoundIdForAdmin(roundId);
    }
}
