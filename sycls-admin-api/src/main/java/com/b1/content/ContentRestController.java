package com.b1.content;

import com.b1.content.dto.ContentAddRequestDto;
import com.b1.content.dto.ContentUpdateRequestDto;
import com.b1.content.dto.ContentUpdateStatusRequestDto;
import com.b1.globalresponse.RestApiResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j(topic = "Content Rest Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ContentRestController {

    private final ContentService contentService;

    @PostMapping("/contents")
    public ResponseEntity<RestApiResponseDto<String>> addContent(
            @Valid @RequestPart("dto") ContentAddRequestDto requestDto,
            @RequestPart(value = "mainImage") MultipartFile mainImage,
            @RequestPart(value = "detailImages", required = false) MultipartFile[] detailImages
    ) {
        contentService.addContent(requestDto, mainImage, detailImages);
        return ResponseEntity.status(HttpStatus.OK).body(RestApiResponseDto.of("등록 성공"));
    }

    @PatchMapping("/contents/{contentId}")
    public ResponseEntity<RestApiResponseDto<String>> updateContent(
            @PathVariable Long contentId,
            @Valid @RequestPart("dto") ContentUpdateRequestDto requestDto,
            @RequestPart(value = "mainImage", required = false) MultipartFile mainImage,
            @RequestPart(value = "detailImages", required = false) MultipartFile[] detailImages
    ) {
        contentService.updateContent(contentId, requestDto, mainImage, detailImages);
        return ResponseEntity.status(HttpStatus.OK).body(RestApiResponseDto.of("정보 수정 성공"));
    }

    @PatchMapping("/contents/{contentId}/status")
    public ResponseEntity<RestApiResponseDto<String>> updateContentStatus(
            @PathVariable Long contentId,
            @Valid @RequestBody ContentUpdateStatusRequestDto requestDto
    ) {
        contentService.updateContentStatus(contentId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(RestApiResponseDto.of("상태 수정 성공"));
    }
}
