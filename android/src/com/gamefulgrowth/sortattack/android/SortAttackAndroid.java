package com.gamefulgrowth.sortattack.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.gamefulgrowth.sortattack.Sort;

public class SortAttackAndroid extends AndroidApplication {
	
	@Override public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        Sort myGame = new Sort();
        initialize(myGame, config);        
    }
}
