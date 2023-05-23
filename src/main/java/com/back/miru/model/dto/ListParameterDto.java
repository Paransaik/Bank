package com.back.miru.model.dto;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ListParameterDto {
    private int start;
    private int currentPerPage;
    private String keyword;
    private String sortKeyword;
    private boolean isPicture;
    private String id;
}
