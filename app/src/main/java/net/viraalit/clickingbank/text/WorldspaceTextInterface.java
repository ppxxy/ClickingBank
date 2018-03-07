package net.viraalit.clickingbank.text;

import net.viraalit.clickingbank.tools.LWJGLMatrix4f;
import net.viraalit.clickingbank.tools.Vector3f;

/**
 * Created by Kim on 1.3.2018.
 */

public class WorldspaceTextInterface extends TextInterface {

	private Vector3f position;
	private LWJGLMatrix4f transformationMatrix;

	public WorldspaceTextInterface(Font font, String text, float fontSize, Vector3f colour, Vector3f position) {
		super(font, text, fontSize, colour);
		this.position = position;
		loadTransformationMatrix();
	}

	private void loadTransformationMatrix(){
		LWJGLMatrix4f matrix = new LWJGLMatrix4f();
		matrix.translate(position);
		matrix.scale(new Vector3f(1f, 1f, 1f));
		this.transformationMatrix = matrix;
	}

	public LWJGLMatrix4f getTransformationMatrix(){
		return this.transformationMatrix;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
		loadTransformationMatrix();
	}
}
