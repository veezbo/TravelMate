package com.travel.travelmate;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.radiumone.effects_sdk.R1PhotoEffectsSDK;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		R1PhotoEffectsSDK.getManager().enable(getApplicationContext(), "3d870b10-fa2c-0130-5ffc-22000afc8c3d", "");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClickView(View v) {
		switch(v.getId()) {
			case R.id.buttonPhotoFX:
				R1PhotoEffectsSDK r1sdk = R1PhotoEffectsSDK.getManager();
				  r1sdk.launchPhotoEffects(this,null,
				  true,
				          new R1PhotoEffectsSDK.PhotoEffectsListener() {            
				                  @Override
				                  public void onEffectsComplete(Bitmap output) {
				                          if( null == output ){
				                                  return;
				                          }
				                          // do something with output   
				                  }
				                  @Override
				                  public void onEffectsCanceled() {
				                          // user canceled                  
				                  }
				          } 
				  );
				  break;
			
		}
	}

}
