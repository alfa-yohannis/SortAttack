package com.gamefulgrowth.sortattack.desktop;

import java.net.InetAddress;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gamefulgrowth.sortattack.Sort;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	
		config.title = Sort.TITLE + " v" +  Sort.VERSION;
		config.useGL30 = false;
//		config.width = 800; config.height = 480;
		config.width = 960; config.height = 540;
//		config.width = 1280; config.height = 720;
//		config.width = 1920; config.height = 1080;
		
		//Sort myGame = new Sort();
		
		try{
			Sort.SERIAL_NUMBER = InetAddress.getLocalHost().getHostName();
		}catch(Exception ex){
			Sort.SERIAL_NUMBER = "DESKTOP";
		}
		new LwjglApplication(new Sort(), config);
	}
}
