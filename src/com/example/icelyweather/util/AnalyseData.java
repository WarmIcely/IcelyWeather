package com.example.icelyweather.util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.example.icelyweather.db.ManagerDB;
import com.example.icelyweather.model.City;
import com.example.icelyweather.model.County;
import com.example.icelyweather.model.Province;

/**
 * @author Administrator 将从服务器中取来的数据解析加入到本地的表格中
 * 将服务器中取回的数据存入到数据库中应该和定义数据库的表格的数据格式（表格中的内容，传入的参数）以及内容对应
 */
public class AnalyseData {
	/**
	 * 从服务器获取的省份信息解析后加载到本地数据库
	 * 
	 * @param database
	 *            本地数据库操作工具向数据库添加数据
	 * @param response
	 *            服务器返回的数据
	 * @return 表示是否解析添加成功
	 */
	public synchronized static boolean analyseProvinceData(ManagerDB database,
			String response) {
		if (!TextUtils.isEmpty(response)) {
			String[] allProvince = response.split(".");
			if (allProvince != null && allProvince.length > 0) {
				for (String p : allProvince) {
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setCode(array[0]);
					province.setName(array[1]);
					database.addProvince(province);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 将城市数据解析存储到本地数据库
	 * 
	 * @param database
	 *            数据库操作存储数据
	 * @param response
	 *            服务器的返回数据
	 * @param provinceId
	 *            表示哪个省份下的城市
	 * @return 是否解析存储成功
	 */
	public synchronized static boolean analyseCityData(ManagerDB database,
			String response, int provinceId) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCity = response.split(".");
			if (allCity != null && allCity.length > 0) {
				for (String c : allCity) {
					String[] array = c.split("\\|");
					City city = new City();
					city.setCode(array[0]);
					city.setName(array[1]);
					city.setProvinceId(provinceId);
					// 将从服务器中解析的数据添加到数据库
					database.addCity(city);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 将县城数据存储到本地数据库
	 * 
	 * @param database
	 *            数据库操作存储数据
	 * @param response
	 *            服务器的返回数据
	 * @param cityId
	 *            所属城市
	 * @return 是否解析添加成功
	 */
	public synchronized static boolean analyseCountyData(ManagerDB database,
			String response, int cityId) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCounty = response.split(".");
			if (allCounty != null && allCounty.length > 0) {
				for (String c : allCounty) {
					String[] array = c.split("\\|");
					County county = new County();
					county.setCode(array[0]);
					county.setName(array[1]);
					county.setCityId(cityId);
					// 将从服务器中解析的数据添加到数据库
					database.addCounty(county);
				}
				return true;
			}
		}
		return false;
	}
	public static void handWeatherInfo(Context context,String response){
		try {
			JSONObject jsonObject = new JSONObject(response);
			JSONObject weatherInfo = jsonObject.getJSONObject(response);
			String cityName = weatherInfo.getString("city");
			String weatherCode = weatherInfo.getString("cityId");
			String lowTemp = weatherInfo.getString("tmp1");
			String highTemp = weatherInfo.getString("temp2");
			String weatherDesp = weatherInfo.getString("weather");
			String publishText = weatherInfo.getString("ptime");
			saveWeatherInfo(context,cityName,weatherCode,lowTemp,highTemp,weatherDesp,publishText);
			} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public static void saveWeatherInfo(Context context,String cityName,String weatherCode,String lowTemp
			,String highTemp,String  weatherDesp,String publishText){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日",Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
context).edit();
		editor.putBoolean("citySelect", true);
		editor.putString("cityName", cityName);
		editor.putString("weatherCode", weatherCode);
		editor.putString("lowTemp", lowTemp);
		editor.putString("highTemp", highTemp);
		editor.putString("weatherDesp", weatherDesp);
		editor.putString("publishText", publishText);
		editor.putString("currentDate", sdf.format(new Date()));
		editor.commit();
	}
}
