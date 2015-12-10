package com.example.icelyweather.service;

import com.example.icelyweather.receiver.AutoUpdateReceiver;
import com.example.icelyweather.util.AnalyseData;
import com.example.icelyweather.util.HttpCallbackListener;
import com.example.icelyweather.util.HttpUtil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

public class AutoUpdateService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
//	service每次被启动时都会启动该方法
//	service应该完成的逻辑都定义在onStart（）和onStartCommand（）方法中，必须使用显示intent启动service
//执行耗时任务时应该开启新线程，因为耗时任务会阻塞UI线程，可以用IntentService方法解决，重写onHandleIntent（）方法完成业务逻辑
	@Override
public int onStartCommand(Intent intent, int flags, int startId) {
	// TODO Auto-generated method stub
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				updateWeather();
			}
			
		}).start();
		AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
		int anHour = 8*60*60*1000;
		long triggerAtTime = SystemClock.elapsedRealtime()+anHour;
		Intent i= new Intent(this,AutoUpdateReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
	return super.onStartCommand(intent, flags, startId);
}
	private void updateWeather(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String weatherCode = prefs.getString("weatherCode", "");
		String address = "http://www.weather.com.cn/data/cityInfo/"+weatherCode+".html";
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				AnalyseData.handWeatherInfo(AutoUpdateService.this, response);
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				e.printStackTrace();
			}
		});
	}
}
