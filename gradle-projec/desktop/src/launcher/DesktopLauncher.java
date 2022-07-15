package launcher;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import vistas.WonderfulWorld;

public class DesktopLauncher{
	
	public static void main (String[] arg) {
		
	      LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	      config.foregroundFPS = 60;
	      config.title = "It's a Wonderful World";
	      config.width =  1600;
	      config.height = 900;
	      new LwjglApplication(new WonderfulWorld(),config);
	   }
	}