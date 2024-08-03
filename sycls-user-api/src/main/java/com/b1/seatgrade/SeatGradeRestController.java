package com.b1.seatgrade;

import com.b1.globalresponse.RestApiResponseDto;
import com.b1.seatgrade.dto.SeatGradeGetAllResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class SeatGradeRestController {

    private final SeatGradeService seatGradeService;

    /**
     * 해당 회차의 좌석-등급 전체 조회
     */
    @GetMapping("/seat-grades")
    public ResponseEntity<RestApiResponseDto<SeatGradeGetAllResponseDto>> getAllSeatGrades(
            @RequestParam(value = "roundId") final Long roundId
    ) {
        SeatGradeGetAllResponseDto responseDto = seatGradeService.getAllSeatGradesUser(roundId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("조회되었습니다.", responseDto));
    }
}
