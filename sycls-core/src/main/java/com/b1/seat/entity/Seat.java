package com.b1.seat.entity;

import com.b1.common.TimeStamp;
import com.b1.place.entity.Place;
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
@Table(name = "seats")
public class Seat extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    @Column(nullable = false, length = 10)
    private String code;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Builder(access = AccessLevel.PRIVATE)
    private Seat(final String code, final SeatStatus status, final Place place) {
        this.code = code;
        this.status = status;
        this.place = place;
    }

    /**
     * 좌석 생성
     */
    public static Seat addSeat(final String code, final Place place) {
        return Seat.builder()
                .code(code)
                .status(SeatStatus.ENABLE)
                .place(place)
                .build();
    }

    /**
     * 좌석 수정
     */
    public void updateSeat(final String code) {
        this.code = code;
    }

    /**
     * 좌석 삭제
     */
    public void deleteSeat() {
        this.status = SeatStatus.DISABLE;
    }
}
