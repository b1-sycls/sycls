package com.b1.seat.entity;

import com.b1.content.entity.Content;
import com.b1.content.entity.Round;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private String grade;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
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
    private SeatGrade(String grade, Integer price, SeatGradeStatus status, Seat seat, Round round,
            Long ticketId) {
        this.grade = grade;
        this.price = price;
        this.status = status;
        this.seat = seat;
        this.round = round;
        this.ticketId = ticketId;
    }
}
