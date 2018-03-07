package net.viraalit.clickingbank.rendering;

import android.content.res.AssetManager;
import android.opengl.GLES20;

import net.viraalit.clickingbank.entities.Camera;
import net.viraalit.clickingbank.entities.Item;
import net.viraalit.clickingbank.models.Model;
import net.viraalit.clickingbank.models.TexturedModel;
import net.viraalit.clickingbank.models.VAO;
import net.viraalit.clickingbank.rendering.uniforms.UniformMatrix;
import net.viraalit.clickingbank.rendering.uniforms.UniformSampler;
import net.viraalit.clickingbank.tools.LWJGLMatrix4f;

import java.io.IOException;

/**
 * Created by Kim on 31.12.2017.
 */

public class ObjectRenderer extends Renderer{

	public ObjectRenderer(AssetManager assets){
		try{
			this.shader = new ObjectShader(assets);
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	public void render(Camera camera, Item item){
		prepare(camera, item.getModel(), item.getTransformationMatrix());
		VAO vao = item.getModel().getVao();
		vao.bind(0, 1, 2);
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, vao.getIndexCount(), GLES20.GL_UNSIGNED_INT, 0);
		vao.unbind(0, 1, 2);
		finish();
	}

	public void prepare(Camera camera, Model model, LWJGLMatrix4f modelsTransformation){
		super.prepare();
		ObjectShader shader = (ObjectShader) this.shader;
		shader.projectionViewMatrix.loadMatrix(camera.getProjectionViewMatrix());
		shader.transformationMatrix.loadMatrix(modelsTransformation);
		((TexturedModel) model).getTexture().bindToUnit(0);
	}

	private class ObjectShader extends ShaderProgram{

		private final int DIFFUSE_TEX_UNIT = 0;

		private static final String VERTEX_SHADER = "shaders/objectVertex.glsl";
		private static final String FRAGMENT_SHADER = "shaders/objectFragment.glsl";

		private UniformMatrix projectionViewMatrix = new UniformMatrix("projectionViewMatrix");
		private UniformMatrix transformationMatrix = new UniformMatrix("transformationMatrix");
		private UniformSampler diffuseMap = new UniformSampler("diffuseMap");


		private ObjectShader(AssetManager assets) throws IOException {
			super(assets.open(VERTEX_SHADER), assets.open(FRAGMENT_SHADER), "position", "textureCoords", "normals");
			super.storeUniformLocations(projectionViewMatrix, transformationMatrix, diffuseMap);
			connectTextureUnits();
		}

		private void connectTextureUnits(){
			super.start();
			diffuseMap.loadTexUnit(DIFFUSE_TEX_UNIT);
			super.stop();
		}
	}
}
