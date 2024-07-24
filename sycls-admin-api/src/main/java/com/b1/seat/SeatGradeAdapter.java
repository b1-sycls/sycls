package com.b1.seat;

import com.b1.seat.entity.SeatGrade;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j(topic = "Seat Grade Adapter")
@Repository
@RequiredArgsConstructor
public class SeatGradeAdapter {

    private final SeatGradeRepository seatGradeRepository;

    public void saveAllSeatGrade(List<SeatGrade> seatGradeList) {
        seatGradeRepository.saveAll(seatGradeList);
    }
}