package com.b1.round;

import com.b1.common.PageResponseDto;
import com.b1.globalresponse.RestApiResponseDto;
import com.b1.round.dto.RoundDetailResponseDto;
import com.b1.round.dto.RoundSearchCondRequest;
import com.b1.round.dto.RoundSimpleUserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "Round Rest Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class RoundRestController {

    private final RoundService roundService;

    @GetMapping("/rounds/{roundId}")
    public ResponseEntity<RestApiResponseDto<RoundDetailResponseDto>> getRound(
            @PathVariable Long roundId
    ) {
        RoundDetailResponseDto responseDto = roundService.getRound(roundId);

        if (responseDto == null) {
            return ResponseEntity.status(HttpStatus.OK).body(RestApiResponseDto.of("회차를 등록해 주세요"));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("정보 조회 성공", responseDto));
    }

    @GetMapping("/rounds")
    public ResponseEntity<RestApiResponseDto<PageResponseDto<RoundSimpleUserResponseDto>>> getAllRounds(
            @ModelAttribute RoundSearchCondRequest request
    ) {
        PageResponseDto<RoundSimpleUserResponseDto> responseDto = roundService.getAllRounds(
                request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("정보 조회 성공", responseDto));
    }
}
