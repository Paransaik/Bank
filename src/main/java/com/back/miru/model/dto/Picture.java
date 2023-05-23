package com.back.miru.model.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@ToString
public class Picture {
    private int pictureIdx;
    private String filepath;
    private String tag;
    private int publicFlag;
    private int isPicture;
    private String id;
    private Timestamp updateTime;
}