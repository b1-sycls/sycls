package com.b1.seatgrade;

import com.b1.round.entity.Round;
import com.b1.seatgrade.entity.SeatGrade;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatGradeRepository extends JpaRepository<SeatGrade, Long> {

    Set<SeatGrade> findAllByRoundAndIdIn(Round round, Set<Long> seatGradeIdList);

    List<SeatGrade> findAllByIdIn(List<Long> seatIdList);

    List<SeatGrade> findAllByRoundId(Long roundId);

    Boolean existsByRoundIdAndSeatIdIn(Long roundId, List<Long> seatIdList);
}
