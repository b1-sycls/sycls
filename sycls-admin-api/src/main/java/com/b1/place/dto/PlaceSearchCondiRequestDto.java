package com.b1.place.dto;

import lombok.Getter;

@Getter
public class PlaceSearchCondiRequestDto {

    // 페이지 정보
    private Integer pageNum = 1; // 기본값 1
    private Integer pageSize = 4; // 기본값 4

    // 검색 조건
    private String location;
    private String name;
    private Integer maxSeat;

    // 정렬 조건
    private String orderBy = "createdAt"; // 정렬할 컬럼명 기본값 createdAt
    private Boolean isDesc = true; // 기본값 true

}
