package com.b1.seatgrade;

import com.b1.seatgrade.entity.SeatGrade;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatGradeRepository extends JpaRepository<SeatGrade, Long> {

    List<SeatGrade> findSeatGradeByRoundId(Long roundId);
}
