package net.viraalit.clickingbank.rendering;

/**
 * Created by Kim on 18.12.2017.
 */

public abstract class Renderer {

	protected ShaderProgram shader;

	public void prepare(){
		shader.start();
	}

	protected void finish(){
		shader.stop();
	}

}
