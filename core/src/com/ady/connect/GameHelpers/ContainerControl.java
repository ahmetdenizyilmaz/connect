package com.ady.connect.GameHelpers;

import com.ady.connect.GameObjects.Container;
import com.ady.connect.GameObjects.Line;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class ContainerControl {
	public Array<Container> containers = new Array<Container>();
	public Array<Line> lines = new Array<Line>();
	private Container choosencontainer1 = null;
	public boolean isallzero = false;
	public boolean changedlist = false;
	public Vector2 dragstart = new Vector2(-200f, -200f);
	public Vector2 dragend = new Vector2(-200f, -200f);
	public int seed = 0;
	public boolean isonenotzero = false;
	public boolean ischanged=false;
	public int endscore = 0;
	public int score=0;
	public float scorebank=0f;
	public float scorefake=0f;
	public float remaintime=0f;

	public ContainerControl(float remaintime) {
		changedlist = true;
		isallzero = false;
		this.remaintime=remaintime;

	}

	public boolean triggeredContainer(Vector2 pos, int event) {
		if(remaintime>0) {
			// System.out.println("asdasdasdasd");
			for (int i = 0; i < containers.size; i++) {
				// System.out.println("a" + i);
				float r = containers.get(i).radius + 30f;
				if (containers.get(i).pos.dst2(pos) <= r * r) {
					if (containers.get(i).getValue() > 0) {
						if (event == 0) {
							clickProcess(i);
						} else if (event == 1) {
							if (releasedProcess(i)) {
								changedlist = true;
								// System.out.println("ayn?de?il");
							}

						}
						return true;
					}
				} else {
					if (event == 0) {
						clickProcess(-1);
					}
				}

			}
		}
		return false;

	}

	private void clickProcess(int index) {
		if (index !=-1 ) {
			for (int i = 0; i < containers.size; i++) {
				containers.get(i).selected = false;
			}
			seed = MathUtils.random(10000);
			if (index != -1) {
				containers.get(index).selected = true;
				choosencontainer1 = containers.get(index);
			} else {
				choosencontainer1 = null;
			}
		}
	}

	public boolean releasedProcess(int index) {

		if (choosencontainer1 == null || choosencontainer1.equals(containers.get(index))) //|| choosencontainer1.valuebank != 0 || containers.get(index).valuebank != 0)
				
			return false;
		Vector2 a = new Vector2(choosencontainer1.pos);
		Vector2 b = new Vector2(containers.get(index).pos);

		lines.add(new Line(a.cpy().lerp(b, 0.5f), b.cpy().sub(a).angle(), a.dst(b), a.cpy(), b.cpy()));
		int min = Math.min(choosencontainer1.getValue(), containers.get(index).getValue());	
		scorebank+=choosencontainer1.getValue()+containers.get(index).getValue()-min;
		choosencontainer1.decreaseValue(min);
		ischanged=true;
		containers.get(index).decreaseValue(min);
		choosencontainer1 = null;
		int temp = 0;
		endscore = 0;
		for (int i = 0; i < containers.size; i++) {
			containers.get(i).selected = false;
			if (containers.get(i).getValue() > 0) {
				temp++;
				endscore += containers.get(i).getValue();
			}
		}
		isallzero = temp == 0;
		isonenotzero = temp == 1;
		// System.out.println("donduuuu");
		return true;
	}
	public void update(float delta)
	{

		if(scorebank>=0.01)
		{
		
			scorefake+=Math.max(0.2f,scorebank/10f)*delta;
			
			scorebank-=Math.max(0.2f,scorebank/10f)*delta;		

			score=(int)(scorefake+0.01);
		}
		else
		{
			scorebank=0;
		}
		
	}
	public int getScore()
	{
		 return (int)(scorefake+0.01+scorebank) ;
	}
	public boolean dragProcess(float x, float y) {
		if (choosencontainer1 == null) {
			dragstart = null;
			dragend = null;
			return false;
		} else {
			// System.out.println(choosencontainer1.pos);
			dragstart = choosencontainer1.pos;
			dragend = new Vector2(x, y);

			return true;
		}

	}

}
