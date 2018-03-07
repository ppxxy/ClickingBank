package net.viraalit.clickingbank.rendering.uniforms;

import android.opengl.GLES20;

/**
 * Created by Kim on 26.12.2017.
 */

public class UniformSampler extends Uniform {

	private int currentValue;
	private boolean used = false;

	public UniformSampler(String name) {
		super(name);
	}

	public void loadTexUnit(int texUnit) {
		if (!used || currentValue != texUnit) {
			GLES20.glUniform1i(super.getLocation(), texUnit);
			used = true;
			currentValue = texUnit;
		}
	}
}
