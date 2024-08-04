package com.b1.ticket.entity;

import com.b1.common.TimeStamp;
import com.b1.round.entity.Round;
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
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tickets")
public class Ticket extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id", nullable = false)
    private Round round;

    @Builder(access = AccessLevel.PRIVATE)
    private Ticket(
            final String code,
            final Integer price,
            final TicketStatus status,
            final LocalDateTime orderDate,
            final User user,
            final Round round
    ) {
        this.code = code;
        this.price = price;
        this.status = status;
        this.orderDate = orderDate;
        this.user = user;
        this.round = round;
    }

    /**
     * 티켓 생성
     */
    public static Ticket addTicket(
            final String orderId,
            final Integer price,
            final User user,
            final Round round
    ) {
        return Ticket.builder()
                .code(orderId)
                .price(price)
                .status(TicketStatus.RESERVED)
                .orderDate(LocalDateTime.now())
                .orderDate(LocalDateTime.now())
                .user(user)
                .round(round)
                .build();
    }
}
