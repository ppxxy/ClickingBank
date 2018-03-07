package net.viraalit.clickingbank.rendering.uniforms;

import android.opengl.GLES20;

import net.viraalit.clickingbank.tools.LWJGLMatrix4f;

/**
 * Created by Kim on 19.12.2017.
 */

public class UniformMatrix extends Uniform {

	public UniformMatrix(String name){
		super(name);
	}

	public void loadMatrix(LWJGLMatrix4f matrix){
		GLES20.glUniformMatrix4fv(super.getLocation(), 1, false, matrix.getValuesAsArray(), 0);
	}
}
