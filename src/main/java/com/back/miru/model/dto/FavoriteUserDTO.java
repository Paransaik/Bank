package com.back.miru.model.dto;

import lombok.*;

@Getter
@ToString
public class FavoriteUserDTO {
    private String id;
    private int pictureIdx;
    private int pictureOrder;
}
