package com.b1.seat.dto;

import com.b1.seat.entity.Seat;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SeatGetAllResponseDto {

    private Integer totalSeats;
    private List<SeatGetResponseDto> seatList;

    public static SeatGetAllResponseDto of(Set<Seat> seatSet) {
        return SeatGetAllResponseDto.builder()
                .totalSeats(seatSet.size())
                .seatList(seatSet.stream().map(SeatGetResponseDto::of).toList())
                .build();
    }
}
