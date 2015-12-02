package com.example.icelyweather.model;

public class City {
//public String name,code,provinceName;
	private String name,code,provinceName;
private int id;
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
		return this.id;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName =provinceName;
	}

	public String getProvinceName() {
		return this.provinceName;
	}
}
