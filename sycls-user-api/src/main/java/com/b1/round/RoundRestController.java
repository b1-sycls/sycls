package com.b1.round;

import com.b1.common.PageResponseDto;
import com.b1.globalresponse.RestApiResponseDto;
import com.b1.round.dto.ContentAndRoundGetResponseDto;
import com.b1.round.dto.RoundDetailResponseDto;
import com.b1.round.dto.RoundSimpleUserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "Round Rest Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class RoundRestController {

    private final RoundService roundService;

    /**
     * 회차 단일 상세 조회
     */
    @GetMapping("/rounds/{roundId}/detail")
    public ResponseEntity<RestApiResponseDto<RoundDetailResponseDto>> getRoundDetail(
            @PathVariable final Long roundId
    ) {
        RoundDetailResponseDto responseDto = roundService.getRoundDetail(roundId);

        if (responseDto == null) {
            return ResponseEntity.status(HttpStatus.OK).body(RestApiResponseDto.of("회차를 등록해 주세요"));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("정보 상세 조회 성공", responseDto));
    }

    /**
     * 회차 단일 상세 조회
     */
    @GetMapping("/rounds/{roundId}")
    public ResponseEntity<RestApiResponseDto<ContentAndRoundGetResponseDto>> getRoundSimple(
            @PathVariable final Long roundId
    ) {
        ContentAndRoundGetResponseDto responseDto = roundService.getRoundSimple(roundId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("정보 간단 조회 성공", responseDto));
    }

    /**
     * 회차 전체 조회
     */
    @GetMapping("/rounds")
    public ResponseEntity<RestApiResponseDto<PageResponseDto<RoundSimpleUserResponseDto>>> getAllRounds(
            @RequestParam(name = "contentId") final Long contentId,
            @RequestParam(name = "page", defaultValue = "1") final int page,
            @RequestParam(name = "sortProperty", defaultValue = "createdAt") final String sortProperty,
            @RequestParam(name = "sortDirection", defaultValue = "DESC") final String sortDirection
    ) {
        PageResponseDto<RoundSimpleUserResponseDto> responseDto = roundService.getAllRounds(
                contentId, page, sortProperty, sortDirection);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("정보 조회 성공", responseDto));
    }
}
