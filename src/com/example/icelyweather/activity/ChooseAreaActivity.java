package com.example.icelyweather.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.icelyweather.R;
import com.example.icelyweather.db.ManagerDB;
import com.example.icelyweather.model.City;
import com.example.icelyweather.model.County;
import com.example.icelyweather.model.Province;
import com.example.icelyweather.util.AnalyseData;
import com.example.icelyweather.util.HttpCallbackListener;
import com.example.icelyweather.util.HttpUtil;

public class ChooseAreaActivity extends Activity {
	public final static int PROVINCE_LEVEL = 0;
	public final static int CITY_LEVEL = 1;
	public final static int COUNTY_LEVEL = 2;
	private TextView titleText;
	private ListView areaList;
	private ArrayAdapter<String> adapter;
	private ProgressDialog progressDialog;
	/**
	 * 填充listview的列表数据
	 */
	private List<String> dataList = new ArrayList<String>();
	/**
	 * 创建数据库
	 */
	private ManagerDB managerDB;
	/**
	 * 省份列表
	 */
	private List<Province> provinceList;
	/**
	 * 城市列表
	 */
	private List<City> cityList;
	/**
	 * 县城列表
	 */
	private List<County> countyList;
	/**
	 * 当前的层级
	 */
	private int selectLevel;
	/**
	 * 选中的省份
	 */
	private Province selectProvince;
	/**
	 * 选中的城市
	 */
	private City selectCity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_area_layout);
		titleText = (TextView) this.findViewById(R.id.title_text);
		areaList = (ListView) this.findViewById(R.id.area_list);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, dataList);
		areaList.setAdapter(adapter);
		managerDB = ManagerDB.getInstance(this);
		areaList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (selectLevel == PROVINCE_LEVEL) {
					selectProvince = provinceList.get(position);
					queryCities();
				} else if (selectLevel == CITY_LEVEL) {
					selectCity = cityList.get(position);
					queryCounties();
				}
			}
		});
		queryProvinces();
	}

	private void queryProvinces() {
		provinceList = managerDB.getProvince();
		if (provinceList.size() > 0) {
			dataList.clear();
			for (Province p : provinceList) {
				dataList.add(p.getName());
			}
			adapter.notifyDataSetChanged();
			areaList.setSelection(0);
			titleText.setText("中国");
			selectLevel = PROVINCE_LEVEL;
		} else {
			queryFormServer(null, "province");
		}
	}

	private void queryCities() {
		cityList = managerDB.getCity(selectProvince.getId());
		if (cityList.size() > 0) {
			dataList.clear();
			for (City c : cityList) {
				dataList.add(c.getName());
			}
			adapter.notifyDataSetChanged();
			areaList.setSelection(0);
			titleText.setText(selectProvince.getName());
			selectLevel = CITY_LEVEL;
		} else {
			queryFormServer(selectProvince.getCode(), "city");
		}
	}

	private void queryCounties() {
		countyList = managerDB.getCounty(selectCity.getId());
		if (countyList.size() > 0) {
			dataList.clear();
			for (County c : countyList) {
				dataList.add(c.getName());
			}
			adapter.notifyDataSetChanged();
			areaList.setSelection(0);
			titleText.setText(selectCity.getName());
			selectLevel = COUNTY_LEVEL;
		} else {
			queryFormServer(selectCity.getCode(), "county");
		}
	}

	private void queryFormServer(final String code, final String tableName) {
		String address;
		if (!TextUtils.isEmpty(code)) {
			address = "http://www.weather.com.cn/data/list3/city" + code
					+ ".xml";
		} else {
			address = "http://www.weather.com.cn/data/list3/city.xml";
		}
		showProgressDialog();
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				// 需要将tableName变为final类型
				boolean result = false;
				if ("province".equals(tableName)) {
					result = AnalyseData.analyseProvinceData(managerDB,
							response);
				} else if ("city".equals(tableName)) {
					result = AnalyseData.analyseCityData(managerDB, response,
							selectProvince.getId());
				} else if ("county".equals(tableName)) {
					result = AnalyseData.analyseCountyData(managerDB, response,
							selectCity.getId());
				}
				if (result) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							// closeProgressDialog();
							if ("province".equals(tableName)) {
								queryProvinces();
							} else if ("city".equals(tableName)) {
								queryCities();
							} else if ("county".equals(tableName)) {
								queryCounties();
							}
						}

					});
				}
			}

			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						closeProgressDialog();
						Toast.makeText(ChooseAreaActivity.this, "加载失败", 1)
								.show();
					}

				});
			}
		});
	}

	void showProgressDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("正在加载。。。");
			progressDialog.setCancelable(false);
		}
		progressDialog.show();
	}

	void closeProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if (selectLevel == COUNTY_LEVEL) {
			queryCities();
		} else

		if (selectLevel == CITY_LEVEL) {
			queryProvinces();
		} else {
			finish();
		}
	}
@Override
	public void onBackPressed() {
		if (selectLevel == COUNTY_LEVEL) {
			queryCities();
		} else if (selectLevel == CITY_LEVEL) {
			queryProvinces();
		} else {
			finish();
		}

	}
}
