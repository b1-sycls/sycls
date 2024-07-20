package com.b1.sycls.domain.place.entity;

import com.b1.sycls.common.entity.TimeStamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "places")
public class Place extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;

    @Column(nullable = false, length = 300)
    private String location;

    @Column(nullable = false)
    private Integer max_seat;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlaceStatus status;

    @Builder(access = AccessLevel.PRIVATE)
    private Place(String location, Integer max_seat, String name, PlaceStatus status) {
        this.location = location;
        this.max_seat = max_seat;
        this.name = name;
        this.status = status;
    }
}