package com.b1.content.dto;

import com.b1.content.entity.ContentStatus;
import lombok.Getter;

@Getter
@Deprecated
public class ContentSearchCondRequest {

    private Long categoryId;
    private ContentStatus status;
    private String titleKeyword = "";
    private int page = 1;
    private String sortProperty = "createdAt";
    private String sortDirection = "DESC";
}
