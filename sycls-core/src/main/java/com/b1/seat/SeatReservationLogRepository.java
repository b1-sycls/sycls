package com.b1.seat;

import com.b1.seat.entity.SeatReservationLog;
import com.b1.seatgrade.entity.SeatGrade;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatReservationLogRepository extends JpaRepository<SeatReservationLog, Long> {

    Set<SeatReservationLog> findAllBySeatGradeInOrderByStartTimeDesc(
            Set<SeatGrade> allSeatGradeByPlace);

}
