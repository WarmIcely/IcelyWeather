package com.example.icelyweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author shuier 对从服务器取来的数据创建sqlite数据库进行管理
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
	 *            上下文
	 * @param name
	 *            数据库的名称
	 * @param factory
	 *            遍历数据库的指针
	 * @param version
	 *            版本号
	 */
	public SqlData(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 执行建表语句，第一次创建数据库时会执行该方法
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREAT_PROVINCE);
		db.execSQL(CREAT_CITY);
		db.execSQL(CREAT_COUNTY);
	}

	/**
	 * 数据库根据版本号进行升级会自动回调该方法
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}
