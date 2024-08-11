package com.b1.cast.entity;

import com.b1.common.TimeStamp;
import com.b1.round.entity.Round;
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
@Table(name = "casts")
public class Cast extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cast_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 300)
    private String imagePath;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CastStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id", nullable = false)
    private Round round;

    @Builder(access = AccessLevel.PRIVATE)
    private Cast(final String name, final String imagePath, final CastStatus status,
            final Round round) {
        this.name = name;
        this.imagePath = imagePath;
        this.status = status;
        this.round = round;
    }

    public static Cast addCast(final String name, final String imagePath, final CastStatus status,
            final Round round) {
        return Cast.builder()
                .name(name)
                .imagePath(imagePath)
                .status(status)
                .round(round)
                .build();
    }

    public void updateCast(final String name, final String imagePath, final CastStatus status) {
        this.name = name;
        this.imagePath = imagePath;
        this.status = status;
    }

    public void updateRoundForCast(final Round round) {
        this.round = round;
    }

    public void deleteCast() {
        this.status = CastStatus.CANCELED;
    }
}
