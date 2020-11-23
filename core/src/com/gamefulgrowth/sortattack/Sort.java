package com.gamefulgrowth.sortattack;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gamefulgrowth.sortattack.screens.Splash;

public class Sort extends Game {

	public final static String TITLE = "Sort Attack";
	public final static String VERSION = "0.0.0007";
	public static HashMap<String, Screen> screens = new HashMap<String, Screen>();
	public static Skin skin;
	public static TextureAtlas atlas;
	public static AssetManager assetManager;
	public static FileHandle logFile;
	public static String SERIAL_NUMBER = "GENERIC";
	private static SimpleDateFormat s;
	public static Preferences pref;
	public static double lattitude;
	public static double longitude;

	@Override
	public void create() {

		pref = Gdx.app.getPreferences("Preferences");
		
		assetManager = new AssetManager();
		
		s = new SimpleDateFormat("yyyyMMddhhmmss");
		if (Gdx.files.isExternalStorageAvailable()){
			logFile = Gdx.files.external("Sort Attack/" + SERIAL_NUMBER + "-SortAttack-log.txt");
			//System.out.println("Using external log file");
		}else{
			logFile = Gdx.files.local("data/" + SERIAL_NUMBER + "-SortAttack-log.txt");
			//System.out.println("Using interal log file");
		}
		writeLogs("Application: Game started");

		// // pixel gray
		// NinePatch pixel = new NinePatch(new Texture(
		// Gdx.files.internal("textures/pixel.png")));
		// pixel.setColor(Color.LIGHT_GRAY);
		// skin.add("pixel.gray", pixel);
		//
		// // pixel dark gray
		// pixel = new NinePatch(new Texture(
		// Gdx.files.internal("textures/pixel.png")));
		// pixel.setColor(Color.DARK_GRAY);
		// skin.add("pixel.darkgray", pixel);

		screens.put(Splash.class.toString(), new Splash());
		setScreen(screens.get(Splash.class.toString()));
	};

	@Override
	public void render() {
		super.render();

	}

	public static void writeLogs(String text) {
		String time = s.format(new Date());
		text = SERIAL_NUMBER +  "\t" + String.valueOf(Sort.lattitude) +  "\t" +
				 String.valueOf(Sort.longitude) +  "\t" + time + "\t" + text + "\n";
		logFile.writeString(text, true);
	}
	
	

}
