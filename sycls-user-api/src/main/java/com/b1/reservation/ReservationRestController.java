package com.b1.reservation;

import com.b1.globalresponse.RestApiResponseDto;
import com.b1.reservation.dto.ReservationAddRequestDto;
import com.b1.reservation.dto.ReservationAddResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ReservationRestController {

    private final ReservationService reservationService;

    @PostMapping("/reservations")
    public ResponseEntity<RestApiResponseDto<ReservationAddResponseDto>> addTicket(
            @Valid @RequestBody final ReservationAddRequestDto requestDto
    ) {
        ReservationAddResponseDto responseDto = reservationService.addReservation(requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("해당 좌석이 예매되었습니다.", responseDto));
    }

}
