package com.b1.content.dto;

import lombok.Getter;

@Getter
@Deprecated
public class ContentSearchCondRequest {

    private Long categoryId;
    private String titleKeyword = "";
    private int page = 1;
    private String sortProperty = "createdAt";
    private String sortDirection = "DESC";
}
