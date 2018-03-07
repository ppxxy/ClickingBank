package net.viraalit.clickingbank.rendering.uniforms;

import android.opengl.GLES20;

/**
 * Created by Kim on 19.12.2017.
 */

public abstract class Uniform {

	private static final int NOT_FOUND = -1;

	private String name;
	private int location;

	protected Uniform(String name){
		this.name = name;
	}

	protected Uniform(String name, int programID){
		this(name);
		this.storeUniformLocation(programID);
	}

	public void storeUniformLocation(int programID){
		location = GLES20.glGetUniformLocation(programID, name);
		if(location == NOT_FOUND){
			throw new RuntimeException("Could not locate Uniform " +name);
		}
	}

	protected int getLocation(){
		return this.location;
	}
}
