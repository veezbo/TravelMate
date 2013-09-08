package com.travel.travelmate;

import java.io.IOException;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.eclipsesource.json.JsonObject;
import com.helper.json.JsonHelper;
import com.http.urls.PearsonURL;
import com.radiumone.effects_sdk.R1PhotoEffectsSDK;

public class MainActivity extends Activity {
	private final int TAKE_PICTURE = 999;
	private final String TAG = "TravelmateMain";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d(TAG,"onCreate()");
		R1PhotoEffectsSDK.getManager().enable(getApplicationContext(), "3d870b10-fa2c-0130-5ffc-22000afc8c3d", "");
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		       .detectNetwork() // or .detectAll() for all detectable problems
		       .penaltyDialog()  //show a dialog
		       .permitNetwork() //permit Network access 
		       .build());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClick(View v) throws IOException {
		switch(v.getId()) {
		case R.id.buttonPhotoFX:
			Log.d(TAG,"buttonPhotoFX clicked");
			Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(takePictureIntent, TAKE_PICTURE);
			break;
		case R.id.top10:
			Log.d(TAG, "top10 clicked");
			//String url = "http://api.pearson.com/v2/travel/topten?limit=1&search=San+Francisco&dist=10000&apikey=D9O5GQEOC5RNPDHSiICpOxGDfulfFxbM";
			String url = PearsonURL.getUrl(1, "San Francisco", 10000);
			JsonObject jo = JsonHelper.readJsonFromUrl(url);
			if (jo == null) {
				Log.d(TAG, "darn null json object");
			}
			Log.d(TAG, url);
			Toast.makeText(getApplicationContext(), jo.get("total").toString(), Toast.LENGTH_LONG).show();
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if(requestCode == TAKE_PICTURE) {
			Bundle extras = intent.getExtras();
			Bitmap mImageBitmap = (Bitmap) extras.get("data");
			R1PhotoEffectsSDK r1sdk = R1PhotoEffectsSDK.getManager();
			r1sdk.launchPhotoEffects(this,
					mImageBitmap,
					true,
					new R1PhotoEffectsSDK.PhotoEffectsListener() {            
				@Override
				public void onEffectsComplete(Bitmap output) {
					if( null == output ){
						return;
					}
					// do something with output   
					Log.d(TAG, "Photo FX will now manipulate image");
					Intent i = new Intent();
				}
				@Override
				public void onEffectsCanceled() {
					Log.d(TAG, "Photo FX canceled");
					// user canceled                  
				}
			} 
					);
		}
	}

}
