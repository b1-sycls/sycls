package com.b1.review.dto;

import com.b1.review.entity.ReviewStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewSearchCondRequestDto {

    private Integer pageNum = 1;
    private Integer pageSize = 4;

    // 검색조건
    private Integer rating;
    private String nickName;
    private ReviewStatus reviewStatus;

    // 정렬조건
    private String orderBy = "createdAt"; // 기본 정렬조건
    private Boolean isDesc = true;

}
