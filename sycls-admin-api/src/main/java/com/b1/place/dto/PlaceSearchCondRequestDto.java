package com.b1.place.dto;

import com.b1.place.entity.PlaceStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlaceSearchCondRequestDto {

    // 페이지 정보
    private Integer pageNum = 1; // 기본값 0

    // 검색
    private String location;
    private String name;

    // 상태 선택
    private PlaceStatus status;

}
