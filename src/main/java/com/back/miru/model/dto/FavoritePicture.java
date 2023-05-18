package com.back.miru.model.dto;

public class FavoritePicture {
    private String id;
    private int followId;
    private int order;

    public FavoritePicture(String id, int followId, int order) {
        this.id = id;
        this.followId = followId;
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getFollowId() {
        return followId;
    }

    public void setFollowId(int followId) {
        this.followId = followId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
