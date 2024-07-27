package com.b1.cast;

import com.b1.cast.dto.CastAddRequestDto;
import com.b1.cast.dto.CastUpdateRequestDto;
import com.b1.globalresponse.RestApiResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class CastRestController {

    private final CastService castService;

    /**
     * 출연진 저장 로직
     */
    @PostMapping("/casts")
    public ResponseEntity<RestApiResponseDto<String>> addCast(
            @Valid @RequestPart("dto") final CastAddRequestDto requestDto,
            @RequestPart("image") final MultipartFile image
    ) {
        castService.addCast(requestDto, image);

        return ResponseEntity.status(HttpStatus.OK).body(RestApiResponseDto.of("등록 성공"));
    }

    /**
     * 출연진 수정 로직
     */
    @PatchMapping("/casts/{castId}")
    public ResponseEntity<RestApiResponseDto<String>> updateCast(
            @PathVariable final Long castId,
            @Valid @RequestPart("dto") final CastUpdateRequestDto requestDto,
            @RequestPart(value = "image", required = false) final MultipartFile image
    ) {
        castService.updateCast(castId, requestDto, image);

        return ResponseEntity.status(HttpStatus.OK).body(RestApiResponseDto.of("수정 성공"));
    }
}
