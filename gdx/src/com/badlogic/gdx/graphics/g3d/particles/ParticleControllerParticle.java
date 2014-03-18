package com.badlogic.gdx.graphics.g3d.particles;

import com.badlogic.gdx.graphics.g3d.particles.values.VelocityDatas.VelocityData;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;


public class ParticleControllerParticle extends Particle{
	public ParticleController controller;
	
	//Position
	public float x, y, z;
	
	//Scale
	public float scale, scaleStart, scaleDiff;
	
	//Rotation
	public Quaternion rotation = new Quaternion();
	
	public void reset(){
		x = y = z = 0;
		scale = 1;
		rotation.idt();
	}
}
