package com.example.icelyweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.icelyweather.R;
import com.example.icelyweather.service.AutoUpdateService;
import com.example.icelyweather.util.AnalyseData;
import com.example.icelyweather.util.HttpCallbackListener;
import com.example.icelyweather.util.HttpUtil;

public class WeatherActivity extends Activity implements OnClickListener {
private LinearLayout weatherInfoLayout;
private TextView  cityNameText;
private TextView  publishText;
private TextView lowTempText;
private  TextView highTempText;
private  TextView  weatherDespText;
private  TextView currentDateText;
private Button switchCity;
private Button refreshWeather;
@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_layout);
		weatherInfoLayout = (LinearLayout)this.findViewById(R.id.weather_lauoyt_info);
		cityNameText = (TextView )this.findViewById(R.id.city_name);
		publishText = (TextView)this.findViewById(R.id.publish_text);
		lowTempText = (TextView)this.findViewById(R.id.low_temp);
		highTempText = (TextView)this.findViewById(R.id.high_temp);
		weatherDespText = (TextView)this.findViewById(R.id.weather_desp);
		currentDateText = (TextView)this.findViewById(R.id.current_date);
		switchCity = (Button)this.findViewById(R.id.switch_city);
		refreshWeather = (Button)this.findViewById(R.id.refresh_weather);
		String countyCode = getIntent().getStringExtra("county_code");
		if(!TextUtils.isEmpty(countyCode)){
			publishText.setText("同步中。。。");
			weatherInfoLayout.setVisibility(View.VISIBLE);
			cityNameText.setVisibility(View.VISIBLE);
			queryWeatherCode(countyCode);
		}else{
			showWeather();
		}
		switchCity.setOnClickListener(this);
		refreshWeather.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.switch_city:
			Intent intent = new Intent(this,ChooseAreaActivity.class);
			intent.putExtra("from_weather_activity", true);
			startActivity(intent);
			finish();
			break;
		case R.id.refresh_weather:
			publishText.setText("同步中。。。");
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			String weatherCode = prefs.getString("weatherCode", "");
			if(!TextUtils.isEmpty(weatherCode)){
				queryWeatherInfo(weatherCode);	
			}
			break;
			default:
				break;
	}
	}
	private void queryWeatherCode(String countyCode){
		String address = "http://www.weather.com.cn/data/list3/city"+countyCode+".xml";
		queryFormServer(address,"countyCode");
	}
	private void queryWeatherInfo(String weatherCode){
		String address = "http://www.weather.com.cn/data/cityinfo"+weatherCode+".htmll";
		queryFormServer(address,"weatherCode");
	}
	private void queryFormServer(final String address,final String code){
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				if("countyCode".equals(code)){
					if(!TextUtils.isEmpty(response)){
						String[] array = response.split("\\|");
						if(array!= null && array.length == 2){
							String weatherCode = array[1];
							queryWeatherInfo(weatherCode);
						}
					}
					
				}		else if("weatherCode".equals(code)){
					if(!TextUtils.isEmpty(code)){
						AnalyseData.handWeatherInfo(WeatherActivity.this, response);
						runOnUiThread(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								showWeather();
							}
							
						});
							
						
					}
				}
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						publishText.setText("同步失败。。。");
					}
					
				});
			}
		});
	}
	private void showWeather(){
//		第三种直接使用PreferenceManager获取SharedPreferences对象的方法，以包名做为默认
//		的存储表名，每次使用SharedPreferences时都要获取，如果写入需要editor并commit
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	cityNameText.setText(prefs.getString("cityName", ""));
	publishText.setText(prefs.getString("publishText", ""));
	lowTempText.setText(prefs.getString("lowTemp", ""));
	highTempText.setText(prefs.getString("highTemp", ""));
	weatherDespText.setText("今天"+prefs.getString("weatherDesp"+"发布", ""));
	currentDateText.setText(prefs.getString("currentDate", ""));
	weatherInfoLayout.setVisibility(View.VISIBLE);
	cityNameText.setVisibility(View.VISIBLE);
	Intent intent = new Intent(WeatherActivity.this,AutoUpdateService.class);
	startService(intent);
	}
}
