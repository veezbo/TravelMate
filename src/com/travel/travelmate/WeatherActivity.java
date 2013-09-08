package com.travel.travelmate;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.helper.json.JsonHelper;
import com.http.urls.WeatherURL;

public class WeatherActivity extends Activity{
	static final String TAG = "WeatherActivity";
	EditText zipCode;
	ArrayList<String> items = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG,"onCreate()");
		setContentView(R.layout.activity_weather);
		ListView listview = (ListView) findViewById(R.id.listViewDays);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
		listview.setAdapter(adapter);
		
		
	}
	
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.buttonForecast:
			zipCode = (EditText) findViewById(R.id.editTextZip);
			String url = "";
			url = WeatherURL.getWeatherUrl("forecast10day",zipCode.getText().toString(),"json");
			Log.d(TAG,"zip code:"+zipCode.getText() + "url:"+url);
			try {
				JsonObject jo = JsonHelper.readJsonFromUrl(url);
				if(jo!=null) {
				Log.d(TAG,"Now parsing json object");
				Log.d(TAG,"Result for get(forecast): "+jo.get("forecast"));
				JsonObject forecast = jo.get("forecast").asObject();
				Log.d(TAG,"Result for forecast.get(simpleforecast): "+forecast.get("simpleforecast"));
				JsonObject txtForecast = forecast.get("txt_forecast").asObject();
				Log.d(TAG,"Result for txtForecast.get(forecastday): "+txtForecast.get("forecastday"));
				JsonArray results = txtForecast.get("forecastday").asArray();
				Log.d(TAG,"Result for jo.get(forecastday): "+jo.get("forecastday"));
				Log.d(TAG,"Result for forecast.get(forecastday): "+forecast.get("forecastday"));
//				JsonArray results = forecast.get("forecastday").asArray();
				for(JsonValue value : results) {
					JsonObject j = value.asObject();
					String icon_url = j.get("icon_url").toString();
					String title = j.get("title").toString();
					String fcttext = j.get("fcttext").toString();
					//add each entry to listview
					items.add(fcttext);
					adapter.notifyDataSetChanged();
					Log.d(TAG,"icon_url:"+icon_url+", title:"+title+", fcttext:"+fcttext);
				}
				} else {Log.e(TAG,"null json object");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
