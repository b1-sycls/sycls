package com.b1.round.entity;

import com.b1.content.entity.Content;
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
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "rounds")
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "round_id")
    private Long id;

    @Column(nullable = false)
    private Integer sequence;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoundStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Builder(access = AccessLevel.PRIVATE)
    private Round(final Integer sequence, final LocalDate startDate, final LocalTime startTime,
            final LocalTime endTime, final Content content, final Place place,
            final RoundStatus status) {
        this.sequence = sequence;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.content = content;
        this.place = place;
        this.status = status;
    }

    public static Round addRound(final Integer sequence, final LocalDate startDate,
            final LocalTime startTime, final LocalTime endTime, final RoundStatus status,
            final Content content, final Place place) {
        return Round.builder()
                .sequence(sequence)
                .startDate(startDate)
                .startTime(startTime)
                .endTime(endTime)
                .status(status)
                .content(content)
                .place(place)
                .build();
    }

    public void updateStatus(final RoundStatus status) {
        this.status = status;
    }

    public void updateDateAndTime(final LocalDate startDate, final LocalTime startTime,
            final LocalTime endTime) {
        this.startDate = startDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void updatePlace(final Place place) {
        this.place = place;
    }
}
