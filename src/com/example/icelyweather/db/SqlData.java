package com.example.icelyweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author shuier �Դӷ�����ȡ�������ݴ���sqlite���ݿ���й���
 */
public class SqlData extends SQLiteOpenHelper {
public static	final  String CREAT_PROVINCE = "create table province ("
			+ "id integer primary key autoincrement," + "province_name text,"
			+ "province_code text)";
public static	final String CREAT_CITY = "create table city ("
			+ "id integer primary key autoincrement," + "city_name text,"
			+ "city_code text," + "province_name)";
public	static final  String CREAT_COUNTY = "create table county ("
			+ "id integer primary key autoincrement," + "county_name text,"
			+ "county_code text," + "city_name)";

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
		db.execSQL(CREAT_PROVINCE);
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
