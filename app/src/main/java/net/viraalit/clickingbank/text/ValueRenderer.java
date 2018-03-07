package net.viraalit.clickingbank.text;

import android.content.res.AssetManager;
import android.opengl.GLES20;

import net.viraalit.clickingbank.entities.Camera;
import net.viraalit.clickingbank.rendering.Renderer;
import net.viraalit.clickingbank.rendering.ShaderProgram;
import net.viraalit.clickingbank.rendering.uniforms.UniformMatrix;
import net.viraalit.clickingbank.rendering.uniforms.UniformVec3;

import java.io.IOException;
import java.util.List;

/**
 * Created by Kim on 25.2.2018.
 */

public class ValueRenderer extends Renderer {


	public ValueRenderer(AssetManager assets){
		try{
			this.shader = new ValueShader(assets);
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	public void render(TextInterface value){
		prepare();
		value.getFont().getTextureAtlas().bind();
		TextInterface textInterface = value;
		textInterface.bindBuffers();
		ValueShader shader = (ValueShader) this.shader;
		shader.colour.loadVector(textInterface.getColour());
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, textInterface.getVertexCount());
		textInterface.unbindBuffers();
		finish();
	}

	public void prepare(){
		super.prepare();
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		GLES20.glDisable(GLES20.GL_DEPTH_TEST);
	}

	@Override
	public void finish(){
		super.finish();
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glDisable(GLES20.GL_BLEND);
	}

	private class ValueShader extends ShaderProgram {

		private static final String VERTEX_FILE = "shaders/valueVertex.glsl";
		private static final String FRAGMENT_FILE = "shaders/valueFragment.glsl";

		private UniformVec3 colour = new UniformVec3("colour");

		private ValueShader(AssetManager assets) throws IOException {
			super(assets.open(VERTEX_FILE), assets.open(FRAGMENT_FILE), "position", "textureCoords");
			super.storeUniformLocations(colour);
		}
	}

}
