package com.b1.seat;

import com.b1.globalresponse.RestApiResponseDto;
import com.b1.seat.dto.SeatAddRequestDto;
import com.b1.seat.dto.SeatGetAllResponseDto;
import com.b1.seat.dto.SeatGetResponseDto;
import com.b1.seat.dto.SeatUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class SeatRestController {

    private final SeatService seatService;

    /**
     * 해당 공연장의 좌석 등록
     */
    @PostMapping("/places/{placeId}/seats")
    public ResponseEntity<RestApiResponseDto<String>> addSeats(
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
        SeatGetAllResponseDto response = seatService.getAllSeats(placeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("조회되었습니다.", response));
    }

    /**
     * 좌석의 상세정보(단건) 조회
     */
    @GetMapping("/seats/{seatId}")
    public ResponseEntity<RestApiResponseDto<SeatGetResponseDto>> getSeat(
            @PathVariable final Long seatId
    ) {
        SeatGetResponseDto response = seatService.getSeat(seatId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("조회되었습니다.", response));
    }

    /**
     * 좌석 수정
     */
    @PatchMapping("/seats/{seatId}")
    public ResponseEntity<RestApiResponseDto<Long>> updateSeat(
            @PathVariable final Long seatId,
            @Valid @RequestBody final SeatUpdateRequestDto requestDto
    ) {
        Long response = seatService.updateSeat(seatId, requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("수정되었습니다.", response));
    }

    /**
     * 좌석 삭제
     */
    @DeleteMapping("/places/{placeId}/seats/{seatId}")
    public ResponseEntity<RestApiResponseDto<String>> deleteSeat(
            @PathVariable final Long placeId,
            @PathVariable final Long seatId
    ) {
        seatService.deleteSeat(placeId, seatId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("수정되었습니다."));
    }


}
