package com.back.miru.model.dto;

import lombok.*;

import java.util.List;

@Getter
@ToString
public class User {
	private String id;
	private String password;
	private String name;
	private String email;
	private String address;
	private String phone; // tel > phone 으로 바뀜, email 변수 추가
	@Singular("interest") private List<Interest> interest;
}
