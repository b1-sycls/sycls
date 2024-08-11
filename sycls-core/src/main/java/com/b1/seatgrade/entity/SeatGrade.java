package com.b1.seatgrade.entity;

import com.b1.round.entity.Round;
import com.b1.seat.entity.Seat;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "seats_grade")
public class SeatGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seats_grade_id")
    private Long id;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private SeatGradeType grade;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatGradeStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id", nullable = false)
    private Round round;

    @Column(name = "ticket_id")
    private Long ticketId;

    @Builder(access = AccessLevel.PRIVATE)
    private SeatGrade(final SeatGradeType grade, final Integer price, final SeatGradeStatus status,
                      final Seat seat, final Round round, final Long ticketId) {
        this.grade = grade;
        this.price = price;
        this.status = status;
        this.seat = seat;
        this.round = round;
        this.ticketId = ticketId;
    }

    /**
     * 좌석-등급 등록
     */
    public static SeatGrade addSeatGrade(final SeatGradeType grade, final Integer price,
                                         final Seat seat, final Round round) {
        return SeatGrade.builder()
                .grade(grade)
                .price(price)
                .seat(seat)
                .round(round)
                .status(SeatGradeStatus.ENABLE)
                .build();
    }

    /**
     * 좌석-등급 수정
     */
    public void updateSeatGrade(final SeatGradeType grade, final Integer price) {
        this.grade = grade;
        this.price = price;
    }

    /**
     * 좌석-등급 삭제
     */
    public void deleteSeatGrade() {
        this.status = SeatGradeStatus.DISABLE;
    }

    /**
     *
     */
    public void soldOutSeatGrade() {
        this.status = SeatGradeStatus.SOLD_OUT;
    }

    /**
     * 티켓 추가
     */
    public void updateTicket(
            final Long ticketId
    ) {
        this.ticketId = ticketId;
    }
}
