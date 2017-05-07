package com.ady.connect.GameObjects;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Container {
	public float x;
	public float y;
	public Vector2 stringpos = new Vector2(0f, 0f);
	private Vector2 randompos= new Vector2(0f, 0f);
	public Vector2 pos;
	public int value;
	public float valuebank;
	private float valuefake;
	public float valuesize=1f;
	public float radius = 50f;
	private float sizelimit=1.4f;
	private float gamescore=0f;
	public boolean selected = false, removed = false;
	private Vector2 temp = new Vector2(0.1f, 0f);



	public Container(Vector2 pos, int value) {
		this.x = pos.x;
		this.y = pos.y;
		this.pos = pos;
		this.value = value;
		valuefake=(float) value;
		
		
	}

	public void update(float delta) {

		if(stringpos.dst2(randompos)<=10f)
		{ 
		 randompos.set(	MathUtils.random(-4f,4f),MathUtils.random(-4f,4f) );	
		 //System.out.println("12312313");
		}
		else
		{
	 	stringpos.lerp(randompos, 0.01f*delta) 	;
		}
		if(sizelimit>1.4f)
		{
			sizelimit/=1.0005f;
		}
		if (valuesize>sizelimit)
		{
			valuesize=1f;
		}
		
		
		if(valuebank>=0.2f*delta)
		{
			valuesize+=0.1f;
			sizelimit+=0.01f;
			valuefake-=Math.max(0.2f,valuebank/10f)*delta;
			
			valuebank-=Math.max(0.2f,valuebank/10f)*delta;		

			value=(int)(valuefake+0.01);
		}
		else
		{
			valuebank=0;
			valuesize=1f;
		}
		
	}
   public void decreaseValue(int value )
   {
	   valuebank+=(float)value;
	   
   }
   public int getValue()
   {
	  
	   return (int)(valuefake+0.01-valuebank) ;
   }


}
