package com.b1.seat;

import com.b1.seat.entity.Seat;
import com.b1.seat.entity.SeatStatus;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    Set<Seat> findAllByPlaceIdAndStatus(Long placeId, SeatStatus seatStatus);

    List<Seat> findAllByIdIn(List<Long> seatIdList);
}
