package com.ady.xpbooster;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Connect extends Game
{

	public static int width = 960;
	public static int height = 540;
	private PlayServices ply;


	public Connect(PlayServices ply)
	{
		this.ply=ply;
	}

	@Override
	public void create()
	{
		 Gdx.input.setCatchBackKey(true);

	   this.setScreen(new SplashScreen(ply));
	}

	@Override
	public void render()
	{
		super.render();


	}


	@Override
	public void dispose()
	{

	}
}
