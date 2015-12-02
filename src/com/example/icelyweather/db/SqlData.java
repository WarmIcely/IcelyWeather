package com.example.icelyweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author shuier �Դӷ�����ȡ�������ݴ���sqlite���ݿ���й���
 */
public class SqlData extends SQLiteOpenHelper {
	final static String CREAT_PROVIENCE = "creat table provience ("
			+ "id integer primary key autoincreament" + "provience_name text"
			+ "provience_code text)";
	final static String CREAT_CITY = "creat table city ("
			+ "id integer primary key autoincreament" + "city_name text"
			+ "city_code text" + "province_name)";
	final static String CREAT_COUNTY = "creat table county ("
			+ "id integer primary key autoincreament" + "county_name text"
			+ "county_code text" + "city_name)";

	/**
	 * @param context
	 *            ������
	 * @param name
	 *            ���ݿ������
	 * @param factory
	 *            �������ݿ��ָ��
	 * @param version
	 *            �汾��
	 */
	public SqlData(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	/**
	 * ִ�н�����䣬��һ�δ������ݿ�ʱ��ִ�и÷���
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREAT_PROVIENCE);
		db.execSQL(CREAT_CITY);
		db.execSQL(CREAT_COUNTY);
	}

	/**
	 * ���ݿ���ݰ汾�Ž����������Զ��ص��÷���
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}
