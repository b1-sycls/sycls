package com.b1.seat;

import com.b1.seat.entity.Seat;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    Set<Seat> findByPlaceId(Long placeId);
}
