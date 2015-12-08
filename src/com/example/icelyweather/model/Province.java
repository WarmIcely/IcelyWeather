package com.example.icelyweather.model;

/**
 * @author shuier
 *获取省份的实体，设置和获取省份名称以及代码
 */
public class Province {
//public String name;
//public String code;
private String name;
private String code;
private int id;//为每个省份添加一个标示符
//	public Province(String name, String code) {
//		// TODO Auto-generated constructor stub
//		this.name = name;
//		this.code = code;
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

	public void setCode(String Code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
