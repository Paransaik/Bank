package com.back.bank.model.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;


public class Employee {
	@Getter
	@ToString
	public static class Entity{
		private String empNo;
		private String nm;
		private String password;
		private String email;
		private Date birthDt;
		private Date enterDt;
		private Date retireDt;
		private Integer deptCd;
		private Integer teamCd;
		private Integer gradeCd;
		private Integer positCd;
		private Integer phoneNo;
		private String addr;
	}
}
