package com.back.miru.model.dto;

import lombok.*;

@Getter
@ToString
public class FavoritePicture {
    private String id;
    private int followId;
    private int order;
}
