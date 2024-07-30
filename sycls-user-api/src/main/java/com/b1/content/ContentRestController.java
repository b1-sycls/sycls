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

    /**
     * 공연 단일 조회
     */
    @GetMapping("/contents/{contentId}")
    public ResponseEntity<RestApiResponseDto<ContentDetailResponseDto>> getContent(
            @PathVariable final Long contentId
    ) {
        ContentDetailResponseDto response = contentService.getContent(contentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("단일 조회 성공", response));
    }

    /**
     * 공연 전체 조회
     */
    @GetMapping("/contents")
    public ResponseEntity<RestApiResponseDto<PageResponseDto<ContentGetUserResponseDto>>> getAllContents(
            @RequestParam(name = "categoryId", required = false) final Long categoryId,
            @RequestParam(name = "titleKeyword", required = false) final String titleKeyword,
            @RequestParam(name = "page", defaultValue = "1") final int page,
            @RequestParam(name = "sortProperty", defaultValue = "createdAt") final String sortProperty,
            @RequestParam(name = "sortDirection", defaultValue = "DESC") final String sortDirection
    ) {
        PageResponseDto<ContentGetUserResponseDto> response = contentService.getAllContents(
                categoryId, titleKeyword, page, sortProperty, sortDirection);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("전체 조회 성공", response));
    }


}
