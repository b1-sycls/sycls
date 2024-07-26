package com.b1.seatgrade;

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

    /**
     * 좌석-등급 등록
     */
    public void saveSeatGrades(List<SeatGrade> seatGradeList) {
        seatGradeRepository.saveAll(seatGradeList);
    }
}
