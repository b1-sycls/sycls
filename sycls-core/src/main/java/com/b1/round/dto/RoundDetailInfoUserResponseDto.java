package com.b1.round.dto;

import com.b1.category.entity.CategoryStatus;
import com.b1.content.entity.ContentStatus;
import com.b1.place.entity.PlaceStatus;
import com.b1.round.entity.RoundStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoundDetailInfoUserResponseDto {

    // 나중에 출연진 추가를위해 미리 같은 필드의 dto 선언
    private Long roundId;
    private Integer sequence;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private RoundStatus roundStatus;

    // 공연 정보
    private Long contentId;
    private String contentTitle;
    private String description;
    private String mainImagePath;
    private ContentStatus contentStatus;

    // 카테고리 정보
    private Long categoryId;
    private String categoryName;
    private CategoryStatus categoryStatus;

    // 공연장 정보
    private Long placeId;
    private String placeName;
    private String placeLocation;
    private PlaceStatus placeStatus;

    public void updateMainImagePath(final String mainImagePath) {
        this.mainImagePath = mainImagePath;
    }
}
