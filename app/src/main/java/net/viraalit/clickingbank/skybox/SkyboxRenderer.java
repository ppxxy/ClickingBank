package net.viraalit.clickingbank.skybox;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.opengl.GLES20;

import net.viraalit.clickingbank.entities.Camera;
import net.viraalit.clickingbank.models.Texture;
import net.viraalit.clickingbank.models.VAO;
import net.viraalit.clickingbank.rendering.Renderer;
import net.viraalit.clickingbank.rendering.ShaderProgram;
import net.viraalit.clickingbank.rendering.uniforms.UniformMatrix;
import net.viraalit.clickingbank.tools.LWJGLMatrix4f;
import net.viraalit.clickingbank.tools.Vector3f;

import java.io.IOException;

/**
 * Created by Kim on 7.3.2018.
 */

public class SkyboxRenderer extends Renderer {

	private static final float SIZE = 180f;

	private int lapTimeInMillis = 300000;
	private int currentTime = 0;

	private long lastFrameTime;

	private float[] vertices = {
			-SIZE,  SIZE, -SIZE,
			-SIZE, -SIZE, -SIZE,
			SIZE, -SIZE, -SIZE,
			SIZE, -SIZE, -SIZE,
			SIZE,  SIZE, -SIZE,
			-SIZE,  SIZE, -SIZE,

			-SIZE, -SIZE,  SIZE,
			-SIZE, -SIZE, -SIZE,
			-SIZE,  SIZE, -SIZE,
			-SIZE,  SIZE, -SIZE,
			-SIZE,  SIZE,  SIZE,
			-SIZE, -SIZE,  SIZE,

			SIZE, -SIZE, -SIZE,
			SIZE, -SIZE,  SIZE,
			SIZE,  SIZE,  SIZE,
			SIZE,  SIZE,  SIZE,
			SIZE,  SIZE, -SIZE,
			SIZE, -SIZE, -SIZE,

			-SIZE, -SIZE,  SIZE,
			-SIZE,  SIZE,  SIZE,
			SIZE,  SIZE,  SIZE,
			SIZE,  SIZE,  SIZE,
			SIZE, -SIZE,  SIZE,
			-SIZE, -SIZE,  SIZE,

			-SIZE,  SIZE, -SIZE,
			SIZE,  SIZE, -SIZE,
			SIZE,  SIZE,  SIZE,
			SIZE,  SIZE,  SIZE,
			-SIZE,  SIZE,  SIZE,
			-SIZE,  SIZE, -SIZE,

			-SIZE, -SIZE, -SIZE,
			-SIZE, -SIZE,  SIZE,
			SIZE, -SIZE, -SIZE,
			SIZE, -SIZE, -SIZE,
			-SIZE, -SIZE,  SIZE,
			SIZE, -SIZE,  SIZE
	};

	private static final int skyboxVertexCount = 36;
	private String[] skyboxTextures = new String[]{
			"skybox/front.png",
			"skybox/back.png",
			"skybox/top.png",
			"skybox/bottom.png",
			"skybox/right.png",
			"skybox/left.png"};

	private VAO vao;
	private Texture texture;

	public SkyboxRenderer(Resources resources){
		lastFrameTime = System.currentTimeMillis();
		try {
			this.shader = new SkyboxShader(resources.getAssets());
		} catch(IOException e){
			e.printStackTrace();
		}
		createVAO();
		createTexture(resources.getAssets());
	}

	private void createVAO(){
		vao = VAO.create();
		vao.bind();
		vao.createAttribute(0, vertices, 3);
		vao.unbind();
		vertices = null;
	}

	public void render(Camera camera){
		long currentFrameTime = System.currentTimeMillis();
		currentTime += currentFrameTime - lastFrameTime;
		currentTime %= lapTimeInMillis;
		lastFrameTime = currentFrameTime;
		prepare((float)currentTime/lapTimeInMillis, camera);
		vao.bind(0);
		texture.bindToUnit(0);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, skyboxVertexCount);
		vao.unbind(0);
		finish();
	}

	public void cleanUp(){
		shader.cleanUp();
		vao.delete();
		texture.delete();
	}

	private void prepare(float rotationPercentage, Camera camera){
		SkyboxShader shader = (SkyboxShader) this.shader;
		shader.start();
		LWJGLMatrix4f temp = LWJGLMatrix4f.load(camera.getProjectionMatrix(), null);
		temp.rotate((float) (rotationPercentage * 2 * Math.PI), new Vector3f(0f, 1f, 0f));
		shader.projectionMatrix.loadMatrix(temp);
		shader.viewMatrix.loadMatrix(camera.getViewMatrix());
	}

	private void createTexture(AssetManager assets){
		try{
			texture = new Texture.Loader().loadCubeMap(skyboxTextures, assets);
		} catch(IOException e){
			System.out.println("Error loading skybox textures.");
			e.printStackTrace();
		}
		skyboxTextures = null;
	}

	private class SkyboxShader extends ShaderProgram {

		private static final String VERTEX_SHADER = "shaders/skyboxVertex.glsl";
		private static final String FRAGMENT_SHADER = "shaders/skyboxFragment.glsl";

		protected UniformMatrix projectionMatrix = new UniformMatrix("projectionMatrix");
		protected UniformMatrix viewMatrix = new UniformMatrix("viewMatrix");

		public SkyboxShader(AssetManager assets) throws IOException {
			super(assets.open(VERTEX_SHADER), assets.open(FRAGMENT_SHADER), "position");
			super.storeUniformLocations(projectionMatrix, viewMatrix);
		}
	}
}
