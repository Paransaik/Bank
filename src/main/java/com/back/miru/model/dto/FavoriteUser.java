package com.back.miru.model.dto;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FavoriteUser {
    private String id;
    private int pictureIdx;
    private int pictureOrder;
}
