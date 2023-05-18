package com.back.miru.model.dto;

public class ConcernArea {
	private String id;
	private String dongcode;
	private String address;

	public ConcernArea() {
		// TODO Auto-generated constructor stub
	}

	public ConcernArea(String id, String dongcode) {
		this.id = id;
		this.dongcode = dongcode;
	}

	public ConcernArea(String id, String dongcode, String address) {
		this.id = id;
		this.dongcode = dongcode;
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDongcode() {
		return dongcode;
	}

	public void setDongcode(String dongcode) {
		this.dongcode = dongcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "ConcernArea [id=" + id + ", dongcode=" + dongcode + ", address=" + address + "]";
	}

}
