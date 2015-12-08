package com.example.icelyweather.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author shuier
 * 包装网络请求的相关操作，开启新线程
 */
public class HttpUtil {
	// private final static String url = null;
	// public static void sendHttpRequest(URL url,httpRequestCallbackListener
	// listener){添加final修饰

	public static void sendHttpRequest(final String address,
			final HttpCallbackListener listener) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection connection = null;
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setReadTimeout(8000);
					connection.setConnectTimeout(8000);
					InputStream in = connection.getInputStream();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(in));
					StringBuffer response = new StringBuffer();
					String line = null;
					while ((line = br.readLine()) != null) {
						response.append(line);
					}
					if (listener != null) {
						listener.onFinish(response.toString());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					if (listener != null) {
						listener.onError(e);
					}
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}

		}).start();
	}
}
