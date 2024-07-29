package com.b1.reservation;

import com.b1.globalresponse.RestApiResponseDto;
import com.b1.reservation.dto.ReservationGetRequestDto;
import com.b1.reservation.dto.ReservationGetResponseDto;
import com.b1.reservation.dto.ReservationReleaseRequestDto;
import com.b1.reservation.dto.ReservationReserveRequestDto;
import com.b1.reservation.dto.ReservationReserveResponseDto;
import com.b1.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ReservationRestController {

    private final ReservationService reservationService;

    /**
     * 예매 등록
     */
    @PostMapping("/reservations/reserve")
    public ResponseEntity<RestApiResponseDto<ReservationReserveResponseDto>> reserveReservation(
            @Valid @RequestBody final ReservationReserveRequestDto requestDto,
            @AuthenticationPrincipal final UserDetailsImpl userDetails
    ) {
        ReservationReserveResponseDto responseDto = reservationService.reserveReservation(
                requestDto,
                userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("해당 좌석이 예매되었습니다.", responseDto));
    }

    /**
     * 예매 조회
     */
    @GetMapping("/reservations/reserve")
    public ResponseEntity<RestApiResponseDto<ReservationGetResponseDto>> getReservation(
            @Valid @RequestBody final ReservationGetRequestDto requestDto,
            @AuthenticationPrincipal final UserDetailsImpl userDetails
    ) {
        ReservationGetResponseDto responseDto = reservationService.getReservation(
                requestDto,
                userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(responseDto));
    }

    /**
     * 예매 취소
     */
    @PostMapping("/reservations/release")
    public ResponseEntity<RestApiResponseDto<String>> releaseReservation(
            @Valid @RequestBody final ReservationReleaseRequestDto requestDto,
            @AuthenticationPrincipal final UserDetailsImpl userDetails
    ) {
        reservationService.releaseReservation(requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("해당 좌석은 취소되었습니다."));
    }

}