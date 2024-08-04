package com.b1.seatgrade;

import com.b1.seatgrade.entity.SeatGrade;
import com.b1.seatgrade.entity.SeatGradeReservationLog;
import com.b1.seatgrade.entity.SeatGradeReservationLogStatus;
import com.b1.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatReservationLogRepository extends JpaRepository<SeatGradeReservationLog, Long> {

    List<SeatGradeReservationLog> findAllBySeatGradeInAndCreatedAtAfterAndStatus(
            Set<SeatGrade> seatGrade,
            LocalDateTime createdAt,
            SeatGradeReservationLogStatus status);

    List<SeatGradeReservationLog> findAllByUserAndCreatedAtAfterAndStatus(
            User user,
            LocalDateTime createdAt,
            SeatGradeReservationLogStatus status);

    Set<SeatGradeReservationLog> findAllByIdInAndUser(Set<Long> reservationIds, User user);

    List<SeatGradeReservationLog> findAllByIdIn(List<Long> id);
}
