package com.b1.place;

import com.b1.place.entity.Place;
import com.b1.place.entity.PlaceStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    Optional<Place> findByIdAndStatus(final Long placeId, final PlaceStatus status);

}
