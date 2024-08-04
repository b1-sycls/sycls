package com.b1.seatgrade;

import com.b1.exception.customexception.SeatGradeAlreadySoldOutException;
import com.b1.exception.customexception.SeatGradeNotFoundException;
import com.b1.exception.errorcode.SeatGradeErrorCode;
import com.b1.round.entity.Round;
import com.b1.seatgrade.dto.SeatGradeUserGetDto;
import com.b1.seatgrade.entity.SeatGrade;
import com.b1.seatgrade.entity.SeatGradeStatus;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Seat Grade Helper")
@Component
@RequiredArgsConstructor
public class SeatGradeHelper {

    private final SeatGradeRepository seatGradeRepository;
    private final SeatGradeQueryRepository seatGradeQueryRepository;

    /**
     * 해당 회차의 좌석-등급 전체 조회
     */
    public List<SeatGradeUserGetDto> getAllSeatGradesUser(final Long roundId) {
        return seatGradeQueryRepository.getAllSeatGradesUser(roundId);
    }

    /**
     * 공연좌석 정보 조회
     *
     * @throws SeatGradeAlreadySoldOutException 이미 매진된 등급 좌석의 경우
     */
    public Set<SeatGrade> getAllSeatGradeByRoundAndSeatGradeIds(
            final Round round,
            final Set<Long> seatGradeIds
    ) {
        Set<SeatGrade> seatGrades = seatGradeRepository
                .findAllByRoundAndIdIn(round, seatGradeIds);
        if (seatGrades.size() != seatGradeIds.size()) {
            log.error("찾을 수 없는 등급 좌석 | request {}", seatGradeIds);
            throw new SeatGradeNotFoundException(SeatGradeErrorCode.NOT_FOUND_SEAT_GRADE);
        }
        SeatGradeStatus.checkEnable(seatGrades);
        return seatGrades;
    }

    /**
     * 공연좌석 전체 조회
     */
    public Set<SeatGrade> getAllSeatGradesByRound(final Round round) {
        return seatGradeRepository
                .findAllByRoundAndStatusNot(round, SeatGradeStatus.DISABLE);
    }
}
