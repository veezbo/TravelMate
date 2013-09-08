package com.travel.travelmate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;

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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.buttonPhotoFX:
			Log.d(TAG,"buttonPhotoFX clicked");
			Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(takePictureIntent, TAKE_PICTURE);
			break;

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
