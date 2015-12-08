package com.example.icelyweather.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.icelyweather.model.City;
import com.example.icelyweather.model.County;
import com.example.icelyweather.model.Province;

/**
 * @author shuier
 * ��װ�����ݿ���ز���
 */
public class ManagerDB {
	// public final String DB_NAME = "IcelyWeather_Data";
	/**
	 * ���ݿ���
	 */
	public  static final  String DB_NAME = "icelyweather_data";
	/**
	 * ���ݿ�汾
	 * public final int VERSION = 1;
	 */
	public static final int VERSION = 1;
//	private SqlData weatherData;
	// private static managerDB dbManager;
	private static ManagerDB dbManager;
	private SQLiteDatabase db;

	// final Context context;
	// public managerDB() {
	// // TODO Auto-generated constructor stub
	// weatherData = new SqlData(context,"IcelyWeatherData",null,1);
	// }
	/**
	 * �������ݿ⣬���췽��˽�л�public managerDB(Context context) {
	 * @param context
	 */
	private ManagerDB(Context context) {
		// TODO Auto-generated constructor stub
		SqlData weatherData = new SqlData(context, DB_NAME, null, VERSION);
		db = weatherData.getWritableDatabase();
	}
/**
 * ��ȡmanagerDBʵ��
 * @param context
 * @return
 */
	// public managerDB getInstance(Context context){
	public synchronized static ManagerDB getInstance(Context context) {
		if (dbManager == null) {
			dbManager = new ManagerDB(context);
		}
		return dbManager;
	}

	/**
	 * ��ʡ�ݵ�ʵ����ӵ����ݿ���
	 * 
	 * @param province
	 */
	public void addProvince(Province province) {
		if (province != null) {
			ContentValues values = new ContentValues();
			values.put("province_name", province.getName());
			values.put("province_code", province.getCode());
//			values.put("province_id", province.getId());
			db.insert("province", null, values);
			//�����ʾʡ�ݵı����
		}
	}

	/**
	 * �������ݿ��д洢������ʡ��
	 * 
	 * @return
	 */
	public List<Province> getProvince() {
		List<Province> list = new ArrayList<Province>();
		// Cursor cursor = new Cursor();
		Cursor cursor = db.query("province", null, null, null, null, null, null);
		//��ѯ��ʾʡ�ݵı���У������Ա��Ϊ��λ���е�
		if (cursor.moveToFirst()) {
			do {
//				Province province = new Province(null, null);
				Province province = new Province();
				province.setName(cursor.getString(cursor
						.getColumnIndex("province_name")));
				province.setCode(cursor.getString(cursor
						.getColumnIndex("province_code")));
				province.setId(cursor.getInt(cursor
						.getColumnIndex("id")));
				//��������id������Ϊ��ʶ�������ڱ����ʱ��û�ж�Ӧ�ȡֵʱӦ�úͱ��������Ӧ
				list.add(province);
			} while (cursor.moveToNext());
		}
		return list;
	}

	/**
	 * �����е�ʵ����ӵ����ݿ���
	 * 
	 * @param province
	 */
	public void addCity(City city) {
		if (city != null) {
			ContentValues values = new ContentValues();
			values.put("city_name", city.getName());
			values.put("city_code", city.getCode());
//			values.put("city_id", city.getId());id����������������Ҫ����
			//CITY��������id�⻹�б�ʾ����ʡ�ݵ�provinceId
			values.put("city_provinceId", city.getProvinceId());
			db.insert("city", null, values);
		}
	}

	/**
	 * ����ĳʡ���µ����г���
	 * 
	 * @return
	 */
	public List<City> getCity(int provinceId) {
		List<City> list = new ArrayList<City>();
		// Cursor cursor = new Cursor();
		Cursor cursor = db.query("city", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
//				City city = new City(null, null, null);
				City city = new City();
				city.setName(cursor.getString(cursor
						.getColumnIndex("city_name")));
				city.setCode(cursor.getString(cursor
						.getColumnIndex("city_code")));
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setProvinceId(provinceId);
				list.add(city);
			} while (cursor.moveToNext());
		}
		return list;
	}

	/**
	 * ���سǵ�ʵ����ӵ����ݿ���
	 * 
	 * @param province
	 */
	public void addCounty(County county) {
		if (county != null) {
			ContentValues values = new ContentValues();
			values.put("county_name", county.getName());
			values.put("county_code", county.getCode());
//			values.put("county_id", county.getId());
			values.put("county_cityId", county.getCityId());
			db.insert("county", null, values);
		}
	}

	/**
	 * �������ݿ��д洢�������س�
	 * 
	 * @return
	 */
	public List<County> getCounty(int cityId) {
		List<County> list = new ArrayList<County>();
		// Cursor cursor = new Cursor();
		Cursor cursor = db.query("county", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
//				County county = new County(null, null, null);
				County county = new County();
				county.setName(cursor.getString(cursor
						.getColumnIndex("county_name")));
				county.setCode(cursor.getString(cursor
						.getColumnIndex("county_code")));
//				county.setId(cursor.getInt(cursor.getColumnIndex("county_id")));
				county.setCityId(cityId);
				list.add(county);
			} while (cursor.moveToNext());
		}
		return list;
	}
}
