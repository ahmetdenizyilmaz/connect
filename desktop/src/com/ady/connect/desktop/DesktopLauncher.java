package com.ady.connect.desktop;

import com.ady.connect.Connect;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher  {
	public static void main (String[] arg) {
        DummyPlayServices dummie= new DummyPlayServices();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=960;
		config.height=540;
		new LwjglApplication(new Connect(dummie), config);
	}
}
