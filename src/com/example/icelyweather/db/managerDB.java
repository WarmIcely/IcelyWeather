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
 * 封装了数据库相关操作
 */
public class ManagerDB {
	// public final String DB_NAME = "IcelyWeather_Data";
	/**
	 * 数据库名
	 */
	public  static final  String DB_NAME = "icelyweather_data";
	/**
	 * 数据库版本
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
	 * 生成数据库，构造方法私有化public managerDB(Context context) {
	 * @param context
	 */
	private ManagerDB(Context context) {
		// TODO Auto-generated constructor stub
		SqlData weatherData = new SqlData(context, DB_NAME, null, VERSION);
		db = weatherData.getWritableDatabase();
	}
/**
 * 获取managerDB实例
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
	 * 将省份的实例添加到数据库中
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
			//插入表示省份的表格中
		}
	}

	/**
	 * 遍历数据库中存储的所有省份
	 * 
	 * @return
	 */
	public List<Province> getProvince() {
		List<Province> list = new ArrayList<Province>();
		// Cursor cursor = new Cursor();
		Cursor cursor = db.query("province", null, null, null, null, null, null);
		//查询表示省份的表格中，都是以表格为单位进行的
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
				//自增长的id主键做为标识符所以在保存的时候没有对应项，取值时应该和表格列名对应
				list.add(province);
			} while (cursor.moveToNext());
		}
		return list;
	}

	/**
	 * 将城市的实例添加到数据库中
	 * 
	 * @param province
	 */
	public void addCity(City city) {
		if (city != null) {
			ContentValues values = new ContentValues();
			values.put("city_name", city.getName());
			values.put("city_code", city.getCode());
//			values.put("city_id", city.getId());id主键自增长，不需要设置
			//CITY除了主键id外还有表示隶属省份的provinceId
			values.put("city_provinceId", city.getProvinceId());
			db.insert("city", null, values);
		}
	}

	/**
	 * 遍历某省份下的所有城市
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
	 * 将县城的实例添加到数据库中
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
	 * 遍历数据库中存储的所有县城
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
