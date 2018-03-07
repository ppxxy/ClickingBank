package net.viraalit.clickingbank;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import net.viraalit.clickingbank.rendering.GameRenderer;
import net.viraalit.clickingbank.rendering.MasterRenderer;
import net.viraalit.clickingbank.tools.Settings;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Kim on 7.3.2018.
 */

public class MainView extends GLSurfaceView{

	private MainRenderer mainRenderer;

	public MainView(Context context){
		super(context);

		setEGLContextClientVersion(2);
		mainRenderer = new MainRenderer(this, context.getAssets());
		setRenderer(mainRenderer);

		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}

	public class MainRenderer implements GLSurfaceView.Renderer{

		private int width, height;
		private AssetManager assets;

		private MasterRenderer masterRenderer;
		private MainView mainView;

		public MainRenderer(MainView view, AssetManager assets){
			this.assets = assets;
			mainView = view;
		}

		public void setMasterRenderer(MasterRenderer masterRenderer){
			this.masterRenderer = masterRenderer;
			mainView.setOnTouchListener(masterRenderer);
			if(masterRenderer instanceof GameRenderer){
				((GameRenderer)masterRenderer).getCamera().updateProjectionMatrix(width, height);
			}
		}

		public void render(){
			masterRenderer.render();
		}

		@Override
		public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
			int[] dimensions = new int[4];
			GLES20.glGetIntegerv(GLES20.GL_VIEWPORT, dimensions, 0);
			Settings.setGLSurfaceSize(dimensions[2], dimensions[3]);

			setMasterRenderer(new GameRenderer(mainView, mainView.getResources()));
			GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		}

		@Override
		public void onSurfaceChanged(GL10 gl10, int width, int height) {
			this.width = width;
			this.height = height;
			Settings.setGLSurfaceSize(width, height);
			if(masterRenderer instanceof GameRenderer){
				((GameRenderer)masterRenderer).getCamera().updateProjectionMatrix(width, height);
				((GameRenderer)masterRenderer).getGame().setRenderViewForQueue(mainView);
			}
		}

		@Override
		public void onDrawFrame(GL10 gl10) {
			GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
			GLES20.glEnable(GLES20.GL_DEPTH_TEST);

			render();
		}
	}
}
