package com.b1.content;

import com.b1.common.PageResponseDto;
import com.b1.content.dto.ContentDetailResponseDto;
import com.b1.content.dto.ContentGetUserResponseDto;
import com.b1.globalresponse.RestApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "Content Rest Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ContentRestController {

    private final ContentService contentService;

    @GetMapping("/contents/{contentId}")
    public ResponseEntity<RestApiResponseDto<ContentDetailResponseDto>> getContent(
            @PathVariable Long contentId
    ) {
        ContentDetailResponseDto response = contentService.getContent(contentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("단일 조회 성공", response));
    }

    @GetMapping("/contents")
    public ResponseEntity<RestApiResponseDto<PageResponseDto<ContentGetUserResponseDto>>> getAllContents(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false, defaultValue = "") String titleKeyword,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "createdAt") String sortProperty,
            @RequestParam(required = false, defaultValue = "DESC") String sortDirection
    ) {
        PageResponseDto<ContentGetUserResponseDto> response = contentService.getAllContents(
                categoryId, titleKeyword, page, sortProperty, sortDirection);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("전체 조회 성공", response));
    }

}
