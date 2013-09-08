package com.travel.travelmate;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

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
    		get.setHeader("Accept", "application/json");
    		
			HttpResponse responseGet = client.execute(get);
			BufferedReader rd = new BufferedReader(new InputStreamReader(responseGet.getEntity().getContent(), "UTF-8"));
			
			JsonObject jo = JsonObject.readFrom(rd);
			ACCESS_TOKEN = jo.get("access_token").toString();
			Log.d(TAG, "got access token: " + ACCESS_TOKEN);
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
				HttpPost post = new HttpPost("https://developer.gm.com/api/v1/account/vehicles/1G6DH5E53C0000003/commands/lockDoor");
				post.setHeader("Authorization", "Bearer " + ACCESS_TOKEN);
				post.setHeader("Accept", "application/json");
				HttpResponse responseGet = client.execute(post);
				BufferedReader rd = new BufferedReader(new InputStreamReader(responseGet.getEntity().getContent(), "UTF-8"));
				JsonObject jo = JsonObject.readFrom(rd);
				if (jo != null) {
					Toast.makeText(getApplicationContext(), "Would have unlocked car if available", Toast.LENGTH_SHORT).show();
				}
			}
			catch (Exception e) {
				Log.d(TAG, "failed to lock car");
			}
			break;
		}
	}
	
	
}
