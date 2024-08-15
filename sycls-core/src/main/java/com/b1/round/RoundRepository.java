package com.b1.round;

import com.b1.place.entity.Place;
import com.b1.round.entity.Round;
import com.b1.round.entity.RoundStatus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundRepository extends JpaRepository<Round, Long> {

    Boolean existsByPlaceIdAndStatus(Long placeId, RoundStatus roundStatus);

    List<Round> findAllByContentId(Long contentId);

    List<Round> findAllByPlace(Place place);

}
