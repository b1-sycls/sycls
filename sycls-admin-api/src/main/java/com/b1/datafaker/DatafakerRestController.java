package com.b1.datafaker;

import com.b1.globalresponse.RestApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class DatafakerRestController {

    private final DatafakerService datafakerService;

    @PostMapping("/datafaker")
    public ResponseEntity<RestApiResponseDto<String>> addDummyData() {
        datafakerService.addDummyData();
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("생성 성공!"));
    }

}
