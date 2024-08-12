package com.b1.seatgrade;

import com.b1.round.entity.Round;
import com.b1.seatgrade.entity.SeatGrade;
import com.b1.seatgrade.entity.SeatGradeStatus;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatGradeRepository extends JpaRepository<SeatGrade, Long> {

    Set<SeatGrade> findAllByRoundAndIdIn(Round round, Set<Long> seatGradeIdList);

    Set<SeatGrade> findAllByRoundAndStatusNot(Round round, SeatGradeStatus seatGradeStatus);

    Set<SeatGrade> findAllByTicketIdIn(List<Long> ticketId);

    Set<SeatGrade> findByTicketId(Long ticketId);

    List<SeatGrade> findAllByIdIn(Set<Long> seatGradeIds);

    List<SeatGrade> findAllByRoundId(Long roundId);

    Boolean existsByRoundIdAndSeatIdInAndStatus(Long roundId, List<Long> seatIdList,
            SeatGradeStatus seatGradeStatus);
}
