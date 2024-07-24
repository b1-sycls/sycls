package com.b1.round;

import com.b1.exception.customexception.CategoryNameDuplicatedException;
import com.b1.exception.customexception.RoundNotFoundException;
import com.b1.exception.errorcode.RoundErrorCode;
import com.b1.round.entity.Round;
import java.time.LocalDate;
import java.time.LocalTime;
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

    public void checkContentConflictingReservation(Long placeId, LocalDate startDate,
            LocalTime startTime, LocalTime endTime) {
        if (queryRepository.existsConflictingRounds(placeId, startDate, startTime, endTime)) {
            log.error("해당 공연장에 중복 예약 발생 | placeId : {}", placeId);
            throw new CategoryNameDuplicatedException(RoundErrorCode.CONFLICTING_RESERVATION);
        }
    }

    public Round findById(Long roundId) {
        return roundRepository.findById(roundId)
                .orElseThrow(() -> new RoundNotFoundException(RoundErrorCode.ROUND_NOT_FOUND));
    }

    public List<Round> getAllRoundsByPlaceId(Long placeId, LocalDate startDate) {
        return queryRepository.getAllRoundsByPlaceId(placeId, startDate);
    }
}