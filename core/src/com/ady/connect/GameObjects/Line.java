package com.ady.connect.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Line {
	public float x;
	public float y;
	public Vector2 pos, point1, point2;
	public float angle;
	public float width;
	public float readyct = 120;
	public ParticleEffect effectline;
	private Array<Texture> linetextures=new Array<Texture>();
	public int texturenumb=0;
	private int selectedtexture=0;

	public Line(Vector2 pos, float angle, float width, Vector2 point1, Vector2 point2) {
		this.pos = pos;
		this.x = pos.x;
		this.y = pos.y;
		this.angle = angle;
		this.width = width;
		this.point1 = point1;
		this.point2 = point2;
		effectline = new ParticleEffect();
		effectline.load(Gdx.files.internal("linepix.eft"), Gdx.files.internal(""));
		effectline.getEmitters().first().getSprite().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		effectline.getEmitters().first().getEmission().setHigh(width/75f, width/50f);
		effectline.start();
	}

	public void pushTexture(Texture text)
	{
		linetextures.add(text);
		texturenumb++;
	}
	
	public Texture obtain()
	{
		int random=MathUtils.random(texturenumb-1);
		return linetextures.get(random);
		
	}
	public void update(float delta) {
		if (readyct > 0) {
			readyct-=2.5f;
		}
		effectline.update(delta);
		Vector2 tempvec = new Vector2();
		tempvec = point1.cpy().lerp(point2, MathUtils.randomTriangular(0f, 1f));
		effectline.setPosition(tempvec.x, tempvec.y);
	}
	

}
