package com.b1.reservation;

import com.b1.globalresponse.RestApiResponseDto;
import com.b1.reservation.dto.ReservationGetDetailResponseDto;
import com.b1.reservation.dto.ReservationGetOccupiedResponseDto;
import com.b1.reservation.dto.ReservationGetResponseDto;
import com.b1.reservation.dto.ReservationReleaseRequestDto;
import com.b1.reservation.dto.ReservationReserveRequestDto;
import com.b1.reservation.dto.ReservationReserveResponseDto;
import com.b1.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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
    @GetMapping("/rounds/{roundId}/reservations/reserve")
    public ResponseEntity<RestApiResponseDto<ReservationGetResponseDto>> getReservation(
            @PathVariable(value = "roundId") final Long roundId,
            @AuthenticationPrincipal final UserDetailsImpl userDetails
    ) {
        ReservationGetResponseDto responseDto = reservationService.getReservation(
                roundId,
                userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(responseDto));
    }

    /**
     * 예매 상세 조회
     */
    @GetMapping("/reservations/reserve/detail")
    public ResponseEntity<RestApiResponseDto<ReservationGetDetailResponseDto>> getReservationDetail(
            @AuthenticationPrincipal final UserDetailsImpl userDetails
    ) {
        log.info("상세 조회");
        ReservationGetDetailResponseDto responseDto = reservationService.getReservationDetail(
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

    /**
     * 점유 중인 좌석 조회
     */
    @GetMapping("/rounds/{roundId}/reservations/occupied")
    public ResponseEntity<RestApiResponseDto<ReservationGetOccupiedResponseDto>> getOccupied(
            @PathVariable("roundId") final Long roundId
    ) {
        ReservationGetOccupiedResponseDto responseDto = reservationService.getOccupied(
                roundId
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(responseDto));
    }

}
