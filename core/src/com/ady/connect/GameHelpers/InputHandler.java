package com.ady.connect.GameHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputHandler implements InputProcessor
{
	private ContainerControl controller;
	private OrthographicCamera camera;
	private Vector3 position=new Vector3();

	public InputHandler(ContainerControl controller, OrthographicCamera camera)
	{
		this.controller = controller;
		this.camera=camera;
	}

	@Override
	public boolean keyDown(int keycode)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		position.set(screenX, screenY, 0);
		camera.unproject(position);
		controller.triggeredContainer(new Vector2(position.x,  position.y), 0);
	
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		position.set(screenX, screenY, 0);
		camera.unproject(position);
		controller.dragend=null;
		controller.dragstart=null;
		controller.triggeredContainer(new Vector2(position.x,  position.y), 1);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		// TODO Auto-generated method stub
		position.set(screenX, screenY, 0);
		camera.unproject(position);
		controller.dragProcess(position.x, position.y);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
