package com.ady.connect.GameObjects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class BgElement {

	public int type;
	public Vector2 pos = new Vector2();
	public Vector2 base = new Vector2();
	private float theta = 0;
	public float angle;
	public float radius, r;
	private float alpha = 0;
	public float size = 1f;
	public float rotation;
	private float deltaalpha;
	private float deltatheta;
	public static float biggest = 0;
	private float step = 0;

	public BgElement(int type, float angle, float radius, float alpha, float theta, float deltaalpha,
			float deltatheta) {
		this.type = type;
		this.angle = angle;
		this.radius = radius;
		this.alpha = alpha;
		this.deltaalpha = deltaalpha;
		this.deltatheta = deltatheta;
		base = new Vector2(0f, 1f);
		base.setLength(radius).setAngle(angle);
		pos = base.cpy().add(480f, 270f);

	}

	public void update(float delta, int effectquality) {
		
		
		step += 1;
		if (step >= (float) (6 - effectquality)) {
			theta += deltatheta * delta *  (6f - (float)effectquality);
			alpha += deltaalpha * delta *  (6f - (float)effectquality);
			formulas(delta * (float) (6 - effectquality));
			pos = base.rotate(angle).cpy().setLength(r).add(480f, 270f);
			step = 0f;
		}
	}

	private void formulas(float delta) {
		if (theta > 359f) {
			theta -= 359f;
		}
		if (alpha > 359f) {
			alpha -= 359f;
		}

		size = MathUtils.sinDeg(alpha + theta) * 1.5f + 2;
		angle = (MathUtils.sinDeg(theta) * 2f + MathUtils.sinDeg(theta * 3f) ) *delta;
		rotation = 360f - angle;
		r = Math.abs(MathUtils.sinDeg(alpha)) * radius *1.4f;

		// System.out.println(alpha + "/" + theta + "/" + r);

	}

}
