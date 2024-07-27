package com.b1.seat;

import com.b1.seat.entity.SeatReservationLog;
import com.b1.seat.entity.SeatReservationLogStatus;
import com.b1.seatgrade.entity.SeatGrade;
import com.b1.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatReservationLogRepository extends JpaRepository<SeatReservationLog, Long> {

    List<SeatReservationLog> findAllBySeatGradeInAndCreatedAtAfterAndStatus(
            Set<SeatGrade> seatGrade,
            LocalDateTime createdAt,
            SeatReservationLogStatus status);

    List<SeatReservationLog> findAllByUserAndCreatedAtAfterAndStatus(
            User user,
            LocalDateTime createdAt,
            SeatReservationLogStatus status);

    Set<SeatReservationLog> findAllByIdInAndUser(Set<Long> reservationIds, User user);
}
