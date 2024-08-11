package com.b1.seatgrade;

import com.b1.globalresponse.RestApiResponseDto;
import com.b1.seatgrade.dto.SeatGradeAddRequestDto;
import com.b1.seatgrade.dto.SeatGradeGetAllResponseDto;
import com.b1.seatgrade.dto.SeatGradeUpdateRequestDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class SeatGradeRestController {

    private final SeatGradeService seatGradeService;

    /**
     * 좌석 등급 등록
     */
    @PostMapping("/seat-grades")
    public ResponseEntity<RestApiResponseDto<String>> addSeatGrades(
            @Valid @RequestBody final SeatGradeAddRequestDto requestDto
    ) {
        seatGradeService.addSeatGrades(requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("등록되었습니다."));
    }

    /**
     * 전체 좌석등급이 설정 완료 확인
     */
    @GetMapping("/seat-grades/confirm")
    public ResponseEntity<RestApiResponseDto<Boolean>> confirmAllSeatSetting(
            @RequestParam(value = "roundId") final Long roundId
    ) {
        Boolean response = seatGradeService.confirmAllSeatSetting(roundId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("확인되었습니다.", response));
    }

    /**
     * 좌석 등급 전체 조회
     */
    @GetMapping("/seat-grades")
    public ResponseEntity<RestApiResponseDto<SeatGradeGetAllResponseDto>> getAllSeatGrades(
            @RequestParam(value = "roundId") final Long roundId
    ) {
        SeatGradeGetAllResponseDto response = seatGradeService.getAllSeatGrades(roundId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("조회되었습니다.", response));
    }

    /**
     * 좌석 등급 수정
     */
    @PatchMapping("/seat-grades")
    public ResponseEntity<RestApiResponseDto<String>> updateSeatGrades(
            @Valid @RequestBody final SeatGradeUpdateRequestDto requestDto
    ) {
        seatGradeService.updateSeatGrades(requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("수정되었습니다."));
    }

    /**
     * 좌석 등급 삭제
     */
    @DeleteMapping("/rounds/{roundId}/seat-grades/{seatGradeId}")
    public ResponseEntity<RestApiResponseDto<String>> deleteSeatGrade(
            @PathVariable(value = "roundId") final Long roundId,
            @PathVariable(value = "seatGradeId") final Long seatGradeId
    ) {
        seatGradeService.deleteSeatGrades(roundId, seatGradeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("삭제되었습니다."));
    }

}
