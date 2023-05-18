package com.back.miru.model.dto;

public class Interest {
	private int no;
	private String dongCode;
	private String id;
	private String dongName;

	public Interest() {}

	public Interest(int no, String dongCode, String id, String dongName) {
		super();
		this.no = no;
		this.dongCode = dongCode;
		this.id = id;
		this.dongName = dongName;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getDongCode() {
		return dongCode;
	}

	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDongName() {
		return dongName;
	}

	public void setDongName(String dongName) {
		this.dongName = dongName;
	};
	
	
}