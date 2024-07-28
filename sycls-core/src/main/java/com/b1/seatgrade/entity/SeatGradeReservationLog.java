package com.b1.seatgrade.entity;

import static com.b1.seatgrade.entity.SeatGradeReservationLogStatus.*;

import com.b1.common.TimeStamp;
import com.b1.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "seats_grade_reservation_logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeatGradeReservationLog extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_grade_reservation_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_grade_id", nullable = false)
    private SeatGrade seatGrade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatGradeReservationLogStatus status;

    @Builder(access = AccessLevel.PRIVATE)
    private SeatGradeReservationLog(
            final SeatGrade seatGrade,
            final User user,
            final SeatGradeReservationLogStatus status
    ) {
        this.seatGrade = seatGrade;
        this.user = user;
        this.status = status;
    }


    public static SeatGradeReservationLog addSeatReservationLog(
            final SeatGrade seatGrade,
            final User user
    ) {
        return SeatGradeReservationLog.builder()
                .seatGrade(seatGrade)
                .user(user)
                .status(ENABLE)
                .build();
    }

    public void deleteReservationStatus() {
        this.status = DISABLE;
    }

}
