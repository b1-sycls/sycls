package com.b1.place.entity;

import com.b1.common.TimeStamp;
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
    private Integer maxSeat;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlaceStatus status;

    @Builder(access = AccessLevel.PRIVATE)
    private Place(final String location, final Integer maxSeat, final String name,
            final PlaceStatus status) {
        this.location = location;
        this.maxSeat = maxSeat;
        this.name = name;
        this.status = status;
    }

    /**
     * 공연장 등록
     */
    public static Place addPlace(
            final String location,
            final Integer maxSeat,
            final String name
    ) {
        return Place.builder()
                .location(location)
                .maxSeat(maxSeat)
                .name(name)
                .status(PlaceStatus.DISABLE)
                .build();
    }

    /**
     * 공연장 장소 및 이름 수정
     */
    public void updatePlaceElse(
            final String location,
            final String name
    ) {
        this.location = location;
        this.name = name;
    }

    /**
     * 공연장 최대 좌석수 수정
     */
    public void updatePlaceMaxSeat(
            final Integer maxSeat
    ) {
        this.maxSeat = maxSeat;
        this.status = PlaceStatus.INACTIVATED;
    }

    /**
     * 공연장 상태 수정
     */
    public void updatePlaceStatus(
            final PlaceStatus status
    ) {
        this.status = status;
    }

    /**
     * 공연장 삭제
     */
    public void deletePlace() {
        this.status = PlaceStatus.DISABLE;
    }
}