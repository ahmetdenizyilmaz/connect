package com.ady.xpbooster.desktop;

import com.ady.xpbooster.Connect;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher  {
	public static void main (String[] arg) {
        DummyPlayServices dummie= new DummyPlayServices();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=540;
		config.height=960;

		new LwjglApplication(new Connect(dummie), config);
	}
}
