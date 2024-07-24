package com.b1.content;

import com.b1.content.dto.RoundUpdateStatusRequestDto;
import com.b1.globalresponse.RestApiResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "Round Rest Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class RoundRestController {

    private final RoundService roundService;

    @PatchMapping("/rounds/{roundId}")
    public ResponseEntity<RestApiResponseDto<String>> updateStatus(
            @PathVariable Long roundId, @Valid @RequestBody RoundUpdateStatusRequestDto requestDto
    ) {
        roundService.updateStatus(roundId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(RestApiResponseDto.of("상태 수정 성공"));
    }
}
