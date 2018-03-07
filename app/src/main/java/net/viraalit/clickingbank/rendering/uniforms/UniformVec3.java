package net.viraalit.clickingbank.rendering.uniforms;

import android.opengl.GLES20;

import net.viraalit.clickingbank.tools.Vector3f;

/**
 * Created by Kim on 1.3.2018.
 */

public class UniformVec3 extends Uniform {

	private Vector3f currentValue;
	private boolean used = false;

	public UniformVec3(String name){
		super(name);
	}

	public void loadVector(Vector3f value){
		if(!used || value != currentValue){
			this.currentValue = value;
			used = true;
			GLES20.glUniform3f(super.getLocation(), value.x, value.y, value.z);
		}
	}
}
