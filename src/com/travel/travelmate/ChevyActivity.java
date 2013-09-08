package com.travel.travelmate;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.eclipsesource.json.JsonObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class ChevyActivity extends Activity {
	
	static final String TAG = "ChevyActivity";
	
	public static final String API_KEY = "0e6d6671e55738c6ec670dfa2";
	public static final String SECRET_KEY = "7c8764c237e376b7";
	public static String ACCESS_TOKEN = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chevy);
		
        try {
        	//Get the Access Token with an HTTP GET Request
    		HttpClient client = new DefaultHttpClient();
    		HttpGet get = new HttpGet("https://developer.gm.com/api/v1/oauth/access_token");
    		get.setHeader("Authorization", getB64Auth(API_KEY, SECRET_KEY));
    		//get.setHeader("Authorization", "Basic " + new String(Base64.encode(((API_KEY+":"+SECRET_KEY).getBytes()))));
    		get.setHeader("Accept", "application/json");
    		
    		
			HttpResponse responseGet = client.execute(get);
			BufferedReader rd = new BufferedReader(new InputStreamReader(responseGet.getEntity().getContent(), "UTF-8"));
			
			JsonObject jo = JsonObject.readFrom(rd);
			ACCESS_TOKEN = jo.get("access_token").toString().replace("\"","");
			Log.d(TAG, "got access token: " + ACCESS_TOKEN);
			Log.d(TAG, jo.toString());
		} 
        catch (Exception e) {
        	Log.d(TAG, "Could not get access token", e);
        }
	}
	private String getB64Auth (String login, String pass) {
		String source=login+":"+pass;
		String ret="Basic "+Base64.encodeToString(source.getBytes(),Base64.URL_SAFE|Base64.NO_WRAP);
		return ret;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClickCar(View v) {
		
		switch(v.getId()) {
		case R.id.lock:
			try {
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost("https://developer-gm-com-fb0q4jueqqt8.runscope.net/api/v1/account/vehicles/1G6DH5E53C0000003/commands/lockDoor");
				
				post.setHeader("Authorization", "Bearer " + ACCESS_TOKEN);
				post.setHeader("Accept", "application/json");
				
	    		StringEntity params = new StringEntity("{\"lockDoorRequest\": { \"delay\":\"0\" }}");
	    		post.setEntity(params);
	    		
				HttpResponse responsePost = client.execute(post);
				BufferedReader rd = new BufferedReader(new InputStreamReader(responsePost.getEntity().getContent(), "UTF-8"));
				String line = rd.readLine().replace("\\", "");
				
				JSONObject jo = new JSONObject(line);
				Log.d(TAG, jo.toString());
				
				String status = jo.getJSONObject("commandResponse").getString("status");
				if (!status.equals("failed")) {
					Toast.makeText(getApplicationContext(), "Successfully Locked Car", Toast.LENGTH_SHORT).show();
				}
			}
			catch (Exception e) {
				Log.d(TAG, "failed to lock car", e);
			}
			break;
		
		case R.id.diagnostics:
			try {
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost("https://developer.gm.com/api/v1/account/vehicles/1G6DH5E53C0000003/commands/diagnostics");
				
				post.setHeader("Authorization", "Bearer " + ACCESS_TOKEN);
				post.setHeader("Accept", "application/json");
				
				StringEntity params = new StringEntity("{\"diagnosticsRequest\": { \"diagnostic\": [ \"ChargeMode\", \"CommuteSchedule\", \"EVBatteryLevel\", \"EVChargeStat\", \"EVEstimatedChargeEnd\", \"EVPlugState\", \"EVPlugVoltage\", \"EVScheduledChargeStart\", \"FuelTankInfo\", \"LastTripDistance\", \"LastTripFuelEconomy\", \"LifetimeEVOdometer\", \"LifetimeFuelEconomy\", \"LifetimeFuelUsed\", \"Odometer\", \"OilLife\", \"TirePressure\", \"VehicleRange\" ] }}");
				post.setEntity(params);
								
				HttpResponse responsePost = client.execute(post);
				BufferedReader rd = new BufferedReader(new InputStreamReader(responsePost.getEntity().getContent(), "UTF-8"));
				
				StringBuilder sb = new StringBuilder();
		        String inputStr;
		        while ((inputStr = rd.readLine()) != null) {
		        	sb.append(inputStr); 
		        }
		        
				Log.d(TAG, sb.toString());
				JSONObject jo = new JSONObject(sb.toString());
				if (jo != null) {
					Toast.makeText(getApplicationContext(), "Diagnostics to Come..", Toast.LENGTH_SHORT).show();
				}
			}
			catch (Exception e) {
				Log.d(TAG, "failed to get diagnostics for car");
			}
			break;
		
		case R.id.start:
			try {
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost("https://developer.gm.com/api/v1/account/vehicles/1G6DH5E53C0000003/commands/start");
				post.setHeader("Authorization", "Bearer " + ACCESS_TOKEN);
				post.setHeader("Accept", "application/json");
				
				StringEntity params = new StringEntity("{\"startRequest\":{\"delay\":\"0\"}}");
				post.setEntity(params);
				
				HttpResponse responsePost = client.execute(post);
				BufferedReader rd = new BufferedReader(new InputStreamReader(responsePost.getEntity().getContent(), "UTF-8"));
				JSONObject jo = new JSONObject(rd.readLine());
				Log.d(TAG, jo.toString());
				String status = jo.getJSONObject("commandResponse").getString("status");
				if (!status.equals("failure")) {
					Toast.makeText(getApplicationContext(), "Successfully Started Car", Toast.LENGTH_SHORT).show();
				}
			}
			catch (Exception e) {
				Log.d(TAG, "failed to start car");
			}
			break;
		case R.id.location:
			try {
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost("https://developer.gm.com/api/v1/account/vehicles/1G6DH5E53C0000003/commands/location");
				post.setHeader("Authorization", "Bearer " + ACCESS_TOKEN);
				post.setHeader("Accept", "application/json");
				HttpResponse responsePost = client.execute(post);
				BufferedReader rd = new BufferedReader(new InputStreamReader(responsePost.getEntity().getContent(), "UTF-8"));
				JSONObject jo = new JSONObject(rd.readLine());
				String status = jo.getJSONObject("commandResponse").getString("status");
				Log.d(TAG, jo.toString());
				
				if (!status.equals("failure")) {
					Toast.makeText(getApplicationContext(), "Retrieved Vehicle Location", Toast.LENGTH_SHORT).show();
				}
			}
			catch (Exception e) {
				
			}
		}
	}
	
	
}
