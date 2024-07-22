package com.b1.ticket.entity;

import com.b1.seat.entity.SeatGrade;
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
@Table(name = "tickets_seats")
public class TicketSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_seat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_grade_id", nullable = false)
    private SeatGrade seatGrade;

    @Builder(access = AccessLevel.PRIVATE)
    private TicketSeat(Ticket ticket, SeatGrade seatGrade) {
        this.ticket = ticket;
        this.seatGrade = seatGrade;
    }

    public static TicketSeat addTicketSeat(
            final Ticket ticket,
            final SeatGrade seatGrade) {
        return TicketSeat.builder()
                .ticket(ticket)
                .seatGrade(seatGrade)
                .build();
    }

}
