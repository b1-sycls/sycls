package com.b1.place.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PlacePageResponseDto {

    private Integer currentPage;
    private Integer totalPage;
    private Integer totalElements;
    private Boolean hasNextPage;
    private List<PlaceGetResponseDto> placeList;

    public static PlacePageResponseDto of(Page<PlaceGetResponseDto> pageResponseDto) {
        return PlacePageResponseDto.builder()
                .currentPage(pageResponseDto.getNumber() + 1)
                .totalPage(pageResponseDto.getTotalPages())
                .totalElements(pageResponseDto.getNumberOfElements())
                .hasNextPage(pageResponseDto.hasNext())
                .placeList(pageResponseDto.getContent().stream().toList())
                .build();
    }

}
