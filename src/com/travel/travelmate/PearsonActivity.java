package com.travel.travelmate;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.helper.json.JsonHelper;
import com.http.urls.PearsonURL;

public class PearsonActivity extends Activity implements OnItemSelectedListener {
	
	static final String TAG = "PearsonActivity";
	
	Spinner spinner;
	String category = "(Select Category)";
	ArrayList<String> items = new ArrayList<String>();
	ArrayAdapter<String> listAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pearson);
		ListView lv = (ListView) findViewById(R.id.listView1);
		listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
		lv.setAdapter(listAdapter);
		
		spinner = (Spinner) findViewById(R.id.categories);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClickSearch(View v) throws IOException {
		
		switch(v.getId()) {
		case R.id.search_nearby:
			Log.d(TAG, "clicked search nearby");
			LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
			Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			//Location location = lm.getLastKnownLocation(lm.getBestProvider(new Criteria(), false));
			if (location == null) {
				Log.d(TAG, "darn null location returned");
				location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			}
			if (location == null) {
				Log.d(TAG, "darn null location returned");
				location = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
			}
			
			float lat = (float) location.getLatitude();
			float lon = (float) location.getLongitude();
			Log.d(TAG, "got location data");
			String url = "";
			if (category.equals("(Select Category)")) {
				url = PearsonURL.getUrlPlaces(10, lat, lon, 10000);
			}
			else {
				url = PearsonURL.getUrlPlaces(10, category, lat, lon, 10000);
			}
			Log.d(TAG, "got pearson url: ");
			Log.d(TAG, url);
			JsonObject jo = JsonHelper.readJsonFromUrl(url);
			if (jo == null) {
				Log.d(TAG, "darn null json object");
			}
			
			JsonArray results = jo.get("results").asArray();
			for (JsonValue value : results) {
				String ptitle, purl;
				JsonObject j = value.asObject();
				ptitle = j.get("title").toString();
				purl = j.get("url").toString();
				
				purl = "http://api.pearson.com" + purl + "&=" + PearsonURL.API_KEY;
				ptitle = ptitle.replace("\"", "");
				purl = purl.replace("\"", "");
				items.add(ptitle+":\n\n"+purl);
				listAdapter.notifyDataSetChanged();
				Log.d(TAG, ptitle);
				Log.d(TAG, purl);
			}
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		category = (String) parent.getItemAtPosition(pos);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {}

}
