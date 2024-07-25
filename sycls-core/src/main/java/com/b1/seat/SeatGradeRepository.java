package com.b1.seat;

import com.b1.content.entity.Round;
import com.b1.seat.entity.SeatGrade;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatGradeRepository extends JpaRepository<SeatGrade, Long> {

    Set<SeatGrade> findAllByRoundAndIdIn(Round round, Set<Long> seatGradeIdList);

}
