package net.viraalit.clickingbank.tools;

import android.opengl.GLES20;
import android.util.Log;

import net.viraalit.clickingbank.models.VAO;

/**
 * Created by Kim on 7.3.2018.
 */

public class Settings {

	public static int glSurfaceWidth, glSurfaceHeight;
	public static float verticalPerPixelSize, horizontalPerPixelSize;
	public static float aspectRatio;

	private static Integer lineHeightInPixels;
	private static final float LINE_HEIGHT = 0.1f;

	public static final VAO vao;

	static{
		vao = VAO.create();
		vao.createAttribute(0, new float[]{-1, 1, -1, -1, 1, 1, 1, -1}, 2);
		vao.unbind();
	}

	public static void checkGLError(String op){
		int error;
		while((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR){
			Log.e("OpenGL ES Error", op +": glError " +error);
		}
	}

	public static void setGLSurfaceSize(int width, int height){
		glSurfaceWidth = width;
		glSurfaceHeight = height;
		aspectRatio = (float) width / (float) height;
		if(lineHeightInPixels != null){
			updatePerPixelSize();
		}
	}

	private static void updatePerPixelSize(){
		verticalPerPixelSize = LINE_HEIGHT / (float) lineHeightInPixels;
		horizontalPerPixelSize = verticalPerPixelSize / aspectRatio;
		System.out.println("VerticalPerPixelSize " +verticalPerPixelSize +", horizontalPerPixelSize" +horizontalPerPixelSize);
	}

	public static void setLineHeightInPixels(int lineHeight) {
		lineHeightInPixels = lineHeight;
		updatePerPixelSize();
	}
}
