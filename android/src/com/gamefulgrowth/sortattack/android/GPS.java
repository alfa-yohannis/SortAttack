package com.gamefulgrowth.sortattack.android;

import com.gamefulgrowth.sortattack.Sort;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class GPS implements LocationListener {

	LocationManager locationManager;
	Context context;

	public GPS(Context context) {
		try {
			this.context = context;
			this.enabledListening();
		} catch (Exception e) {
			Log.e("ALFA 01", e.getMessage());
		}
	}

	public void enabledListening() {
		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				2000, 1, this);
	}

	public void disabledListening() {
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		try {
			Sort.lattitude = location.getLatitude();
			Sort.longitude = location.getLongitude();
//			Toast.makeText(context, String.valueOf(lattitude) + " " + String.valueOf(longitude)
//			, Toast.LENGTH_SHORT).show(); 
		} catch (Exception e) {
			Log.e("ALFA 02", e.getMessage());
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		try {
			if (status == LocationProvider.AVAILABLE) {
				this.enabledListening();
			} else if (status == LocationProvider.OUT_OF_SERVICE) {
				this.disabledListening();
			} else if (status == LocationProvider.TEMPORARILY_UNAVAILABLE) {
				this.disabledListening();
			}
		} catch (Exception e) {
			Log.e("ALFA 03", e.getMessage());
		}

	}

	@Override
	public void onProviderEnabled(String provider) {
		try {
			this.enabledListening();
		} catch (Exception e) {
			Log.e("ALFA 04", e.getMessage());
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		try {
			this.disabledListening();
		} catch (Exception e) {
			Log.e("ALFA 05", e.getMessage());
		}
	}

}
