package com.gamefulgrowth.sortattack.android;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.gamefulgrowth.sortattack.Sort;

public class AndroidLauncher extends AndroidApplication {
	
	GPS gps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// // Create the layout
		// RelativeLayout layout = new RelativeLayout(this);
		//
		// // Do the stuff that initialize() would do for you
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// getWindow().clearFlags(
		// WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		//
		//
		// // Create the libgdx View
		// View gameView = initializeForView(new HelloWorld(), false);
		//
		// // Create and setup the AdMob view
		// // AdView adView = new AdView(this, AdSize.BANNER, "xxxxxxxx"); //
		// Put in your secret key here
		// // adView.loadAd(new AdRequest());
		//
		// // Add the libgdx view
		// layout.addView(gameView);

		// //----------------------------------------------------------------------------------
		// Set android application configuration
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		// Initialize for Sort Attack
		TelephonyManager tManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		String uid = tManager.getDeviceId();

		gps = new GPS(this.getApplicationContext());

		Sort myGame = new Sort();
		Sort.SERIAL_NUMBER = uid;

		// //----------------------------------------------------------------------------------

		initialize(myGame, config);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		gps.disabledListening();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		gps.enabledListening();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		gps.disabledListening();
	}
}
