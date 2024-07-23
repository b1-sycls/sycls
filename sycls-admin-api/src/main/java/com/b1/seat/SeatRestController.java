package com.b1.seat;

import com.b1.globalresponse.RestApiResponseDto;
import com.b1.seat.dto.SeatAddRequestDto;
import com.b1.seat.dto.SeatGetAllResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "Seat Rest Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class SeatRestController {

    private final SeatService seatService;

    /**
     * 해당 공연장의 좌석 등록
     */
    @PostMapping("/places/{placeId}/seats")
    public ResponseEntity<RestApiResponseDto> addSeats(
            @PathVariable final Long placeId,
            @Valid @RequestBody final SeatAddRequestDto requestDto
    ) {
        seatService.addSeats(placeId, requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("등록되었습니다."));
    }

    /**
     * 해당 공연장의 좌석 전체 조회
     */
    @GetMapping("/places/{placeId}/seats")
    public ResponseEntity<RestApiResponseDto<SeatGetAllResponseDto>> getAllSeats(
            @PathVariable final Long placeId
    ) {
        SeatGetAllResponseDto responseList = seatService.getAllSeats(placeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("등록되었습니다.", responseList));
    }

}
