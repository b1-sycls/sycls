package com.b1.seatgrade;

import com.b1.seatgrade.dto.SeatGradeGetResponseDto;
import com.b1.seatgrade.entity.SeatGrade;
import java.util.List;
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
     * 좌석-등급 등록
     */
    public void saveSeatGrades(final List<SeatGrade> seatGradeList) {
        seatGradeRepository.saveAll(seatGradeList);
    }

    /**
     * 좌석 등급 전체 조회
     */
    public List<SeatGradeGetResponseDto> getAllSeatGrades(final Long roundId) {
        return seatGradeQueryRepository.getAllSeatGrades(roundId);
    }

    /**
     * 좌석-등급 ID In절 조회
     */
    public List<SeatGrade> findAllByIdIn(final List<Long> seatIdList) {
        return seatGradeRepository.findAllByIdIn(seatIdList);
    }
}
