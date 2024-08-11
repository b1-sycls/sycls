package com.b1.place;

import com.b1.common.PageResponseDto;
import com.b1.globalresponse.RestApiResponseDto;
import com.b1.place.dto.PlaceAddRequestDto;
import com.b1.place.dto.PlaceGetResponseDto;
import com.b1.place.dto.PlaceSearchCondRequestDto;
import com.b1.place.dto.PlaceUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class PlaceRestController {

    private final PlaceService placeService;

    /**
     * 공연장 등록
     */
    @PostMapping("/places")
    public ResponseEntity<RestApiResponseDto<String>> addPlace(
            @Valid @RequestBody final PlaceAddRequestDto requestDto
    ) {
        placeService.addPlace(requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("등록되었습니다."));
    }

    /**
     * 공연장 전체 조회
     */
    @GetMapping("/places")
    public ResponseEntity<RestApiResponseDto<PageResponseDto<PlaceGetResponseDto>>> getAllPlace(
            @ModelAttribute final PlaceSearchCondRequestDto requestDto
    ) {
        PageResponseDto<PlaceGetResponseDto> responseDto = placeService.getAllPlaces(requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("조회되었습니다.", responseDto));
    }

    /**
     * 공연장 단건 조회
     */
    @GetMapping("/{placeId}")
    public ResponseEntity<RestApiResponseDto<PlaceGetResponseDto>> getPlace(
            @PathVariable final Long placeId
    ) {
        PlaceGetResponseDto responseDto = placeService.getPlace(placeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("조회되었습니다", responseDto));
    }

    /**
     * 공연장 정보 수정
     */
    @PatchMapping("/places/{placeId}")
    public ResponseEntity<RestApiResponseDto<Long>> updatePlace(
            @PathVariable final Long placeId,
            @Valid @RequestBody final PlaceUpdateRequestDto requestDto
    ) {
        Long response = placeService.updatePlace(placeId, requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("수정되었습니다.", response));
    }

    /**
     * 공연장 삭제
     */
    @DeleteMapping("/places/{placeId}")
    public ResponseEntity<RestApiResponseDto<String>> deletePlace(
            @PathVariable final Long placeId
    ) {
        placeService.deletePlace(placeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("삭제되었습니다."));
    }

}
