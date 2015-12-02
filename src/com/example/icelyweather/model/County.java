package com.example.icelyweather.model;

public class County {
	// String name, code, cityName;
	private String name, code, cityName;
	private int id;

	// public County(String name, String code, String cityName) {
	// // TODO Auto-generated constructor stub
	// this.name = name;
	// this.code = code;
	// this.cityName = cityName;
	// }
	public void setId(int id) {
		this.id = id;
	}
//	public int getId(County conuty) {
//		return county.id;
//	}
	public int getId() {
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

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityName() {
		return this.cityName;
	}
}
