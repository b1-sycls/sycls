package com.b1.round.dto;

import com.b1.round.entity.RoundStatus;
import lombok.Getter;

@Getter
@Deprecated
public class RoundSearchCondRequest {

    private Long contentId;
    private RoundStatus status;
    private int page = 1;
    private String sortProperty = "createdAt";
    private String sortDirection = "DESC";
}
