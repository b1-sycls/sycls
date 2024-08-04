package com.b1.seatgrade;

import com.b1.round.entity.Round;
import com.b1.seatgrade.entity.SeatGrade;
import com.b1.seatgrade.entity.SeatGradeStatus;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatGradeRepository extends JpaRepository<SeatGrade, Long> {

    Set<SeatGrade> findAllByRoundAndIdIn(Round round, Set<Long> seatGradeIdList);

    Boolean existsByRoundIdAndSeatIdAndStatus(Long roundId, Long seatId, SeatGradeStatus status);

    Set<SeatGrade> findAllByRoundAndStatusNot(Round round, SeatGradeStatus seatGradeStatus);
}
