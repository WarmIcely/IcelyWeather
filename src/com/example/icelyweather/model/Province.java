package com.example.icelyweather.model;

/**
 * @author shuier
 *��ȡʡ�ݵ�ʵ�壬���úͻ�ȡʡ�������Լ�����
 */
public class Province {
//public String name;
//public String code;
private String name;
private String code;
private int id;//Ϊÿ��ʡ�����һ����ʾ��
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
