package com.back.miru.model.dto;


import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Interest {
	private int no;
	private String dongCode;
	private String id;
	private String dongName;
}