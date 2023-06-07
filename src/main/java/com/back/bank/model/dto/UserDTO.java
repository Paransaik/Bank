package com.back.bank.model.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserDTO {
	private String id;
	private String password;
	private String name;
	private String email;
	private String address;
	private String phone; // tel > phone 으로 바뀜, email 변수 추가
}
