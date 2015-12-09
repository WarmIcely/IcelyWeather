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
 * @author Administrator ���ӷ�������ȡ�������ݽ������뵽���صı����
 * ����������ȡ�ص����ݴ��뵽���ݿ���Ӧ�úͶ������ݿ�ı������ݸ�ʽ������е����ݣ�����Ĳ������Լ����ݶ�Ӧ
 */
public class AnalyseData {
	/**
	 * �ӷ�������ȡ��ʡ����Ϣ��������ص��������ݿ�
	 * 
	 * @param database
	 *            �������ݿ�������������ݿ��������
	 * @param response
	 *            ���������ص�����
	 * @return ��ʾ�Ƿ������ӳɹ�
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
	 * ���������ݽ����洢���������ݿ�
	 * 
	 * @param database
	 *            ���ݿ�����洢����
	 * @param response
	 *            �������ķ�������
	 * @param provinceId
	 *            ��ʾ�ĸ�ʡ���µĳ���
	 * @return �Ƿ�����洢�ɹ�
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
					// ���ӷ������н�����������ӵ����ݿ�
					database.addCity(city);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * ���س����ݴ洢���������ݿ�
	 * 
	 * @param database
	 *            ���ݿ�����洢����
	 * @param response
	 *            �������ķ�������
	 * @param cityId
	 *            ��������
	 * @return �Ƿ������ӳɹ�
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
					// ���ӷ������н�����������ӵ����ݿ�
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��M��d��",Locale.CHINA);
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
