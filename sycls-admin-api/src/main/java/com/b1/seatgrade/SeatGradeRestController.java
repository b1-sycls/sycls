package com.b1.seatgrade;

import com.b1.globalresponse.RestApiResponseDto;
import com.b1.seatgrade.dto.SeatGradeAddRequestDto;
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

}
