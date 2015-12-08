package com.example.icelyweather.model;

public class City {
//public String name,code,provinceName;½«provincename»»³ÉprovinceId
	private String name,code;
private int id;
private int provinceId;
//	public City(String name,String code,String provinceName) {
//		// TODO Auto-generated constructor stub
//		this.name = name;
//		this.code= code;
//		this.provinceName = provinceName;
//	}
	public void setId(int id) {
		this.id = id;
	}

	public int  getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId =provinceId;
	}

	public int getProvinceId() {
		return provinceId;
	}
}
