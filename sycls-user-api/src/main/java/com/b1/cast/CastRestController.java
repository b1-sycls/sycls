package com.b1.cast;

import com.b1.cast.entity.dto.CastGetUserResponseDto;
import com.b1.globalresponse.RestApiResponseDto;
import java.util.List;
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
public class CastRestController {

    private final CastService castService;

    /**
     * 출연진 조회 로직
     */
    @GetMapping("/casts")
    public ResponseEntity<RestApiResponseDto<List<CastGetUserResponseDto>>> getAllCasts(
            @RequestParam(value = "roundId") final Long roundId
    ) {
        List<CastGetUserResponseDto> responseDto = castService.getAllCastsByRoundId(roundId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("조회 성공", responseDto));
    }

}
