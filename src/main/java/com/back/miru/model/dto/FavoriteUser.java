package com.back.miru.model.dto;

public class FavoriteUser {
    private String id;
    private int pictureIdx;
    private int pictureOrder;

    public FavoriteUser(String id, int pictureIdx, int pictureOrder) {
        this.id = id;
        this.pictureIdx = pictureIdx;
        this.pictureOrder = pictureOrder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPictureIdx() {
        return pictureIdx;
    }

    public void setPictureIdx(int pictureIdx) {
        this.pictureIdx = pictureIdx;
    }

    public int getPictureOrder() {
        return pictureOrder;
    }

    public void setPictureOrder(int pictureOrder) {
        this.pictureOrder = pictureOrder;
    }
}
