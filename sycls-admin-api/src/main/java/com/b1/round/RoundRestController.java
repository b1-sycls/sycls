package com.b1.round;

import com.b1.common.PageResponseDto;
import com.b1.globalresponse.RestApiResponseDto;
import com.b1.round.dto.RoundAddRequestDto;
import com.b1.round.dto.RoundDetailResponseDto;
import com.b1.round.dto.RoundSearchCondRequest;
import com.b1.round.dto.RoundSimpleAdminResponseDto;
import com.b1.round.dto.RoundUpdateRequestDto;
import com.b1.round.dto.RoundUpdateStatusRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * 회차 전체 조회
     */
    // 공연정보 상관없이 전체조회할 일이 있을지도 모른다고 생각해서 contentId를 동적쿼리화
    // 공연정보에 해당하는 것만 불러온다면 PathVariable 로 변경
    @GetMapping("/rounds")
    public ResponseEntity<RestApiResponseDto<PageResponseDto<RoundSimpleAdminResponseDto>>> getAllRounds(
            @ModelAttribute final RoundSearchCondRequest request
    ) {
        PageResponseDto<RoundSimpleAdminResponseDto> responseDto = roundService.getAllRounds(
                request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("전체 정보 조회 성공", responseDto));
    }
}
