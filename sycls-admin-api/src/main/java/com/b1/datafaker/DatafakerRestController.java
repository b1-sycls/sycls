package com.b1.datafaker;

import com.b1.globalresponse.RestApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class DatafakerRestController {

    private final DatafakerService datafakerService;

    @PostMapping("/datafaker/place")
    public ResponseEntity<RestApiResponseDto<Long>> addDummyDataPlace() {
        Long l = datafakerService.addDummyDataPlace();
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(l));
    }

    @PostMapping("/datafaker/seat/{placeId}")
    public ResponseEntity<RestApiResponseDto<String>> addDummyDataSeat(
            @PathVariable Long placeId
    ) {
        datafakerService.addDummyDataSeat(placeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("생성 성공!"));
    }

    @PostMapping("/datafaker/round/{placeId}")
    public ResponseEntity<RestApiResponseDto<String>> addDummyDataRound(
            @PathVariable Long placeId
    ) {
        datafakerService.addDummyDataRound(placeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("생성 성공!"));
    }

    @PostMapping("/datafaker/seat-grade")
    public ResponseEntity<RestApiResponseDto<String>> addDummyDataSeatGrade(
    ) {
        datafakerService.addDummyDataSeatGrade();
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("생성 성공!"));
    }

}
