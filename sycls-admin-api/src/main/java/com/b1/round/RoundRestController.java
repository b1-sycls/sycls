package com.b1.round;

import com.b1.common.PageResponseDto;
import com.b1.globalresponse.RestApiResponseDto;
import com.b1.round.dto.RoundAddRequestDto;
import com.b1.round.dto.RoundDetailResponseDto;
import com.b1.round.dto.RoundSimpleAdminResponseDto;
import com.b1.round.dto.RoundUpdateRequestDto;
import com.b1.round.dto.RoundUpdateStatusRequestDto;
import com.b1.round.entity.RoundStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
     * 회차 등록
     */
    @PostMapping("/rounds")
    public ResponseEntity<RestApiResponseDto<String>> addRound(
            @Valid @RequestBody final RoundAddRequestDto requestDto) {
        roundService.addRound(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(RestApiResponseDto.of("등록 성공"));
    }

    /**
     * 회차 상태 수정
     */
    @PatchMapping("/rounds/{roundId}/status")
    public ResponseEntity<RestApiResponseDto<String>> updateRoundStatus(
            @PathVariable final Long roundId,
            @Valid @RequestBody final RoundUpdateStatusRequestDto requestDto) {
        roundService.updateRoundStatus(roundId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(RestApiResponseDto.of("상태 수정 성공"));
    }

    /**
     * 회차 정보 수정
     */
    @PatchMapping("/rounds/{roundId}")
    public ResponseEntity<RestApiResponseDto<String>> updateRound(
            @PathVariable final Long roundId,
            @Valid @RequestBody final RoundUpdateRequestDto requestDto) {
        roundService.updateRound(roundId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(RestApiResponseDto.of("정보 수정 성공"));
    }

    /**
     * 회차 단일 조회
     */
    @GetMapping("/rounds/{roundId}")
    public ResponseEntity<RestApiResponseDto<RoundDetailResponseDto>> getRound(
            @PathVariable final Long roundId
    ) {
        RoundDetailResponseDto responseDto = roundService.getRound(roundId);

        if (responseDto == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(RestApiResponseDto.of("회차를 등록해 주세요."));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("단일 정보 조회 성공", responseDto));
    }

    /**
     * 공연장의 회차 전체 조회
     */
    @GetMapping("/rounds")
    public ResponseEntity<RestApiResponseDto<PageResponseDto<RoundSimpleAdminResponseDto>>> getAllRounds(
            @RequestParam(name = "contentId") final Long contentId,
            @RequestParam(name = "status", required = false) final RoundStatus status,
            @RequestParam(name = "page", defaultValue = "1") final int page,
            @RequestParam(name = "sortProperty", defaultValue = "createdAt") final String sortProperty,
            @RequestParam(name = "sortDirection", defaultValue = "DESC") final String sortDirection
    ) {
        PageResponseDto<RoundSimpleAdminResponseDto> responseDto = roundService.getAllRounds(
                contentId, status, page, sortProperty, sortDirection);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("정보 조회 성공", responseDto));
    }
}
