package com.back.miru.model.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteUser {
    private String id;
    private int pictureIdx;
    private int pictureOrder;

    @Builder
    public FavoriteUser(String id, int pictureIdx, int pictureOrder) {
        this.id = id;
        this.pictureIdx = pictureIdx;
        this.pictureOrder = pictureOrder;
    }
}
