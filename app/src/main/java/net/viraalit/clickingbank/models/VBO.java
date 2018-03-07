package net.viraalit.clickingbank.models;

import android.opengl.GLES20;

import net.viraalit.clickingbank.tools.Buffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by Kim on 19.12.2017.
 */

public class VBO {

	private int id, type;

	protected VBO(int id, int type){
		this.id = id;
		this.type = type;
	}

	protected VBO(int type){
		this.id = getFreeId();
		this.type = type;
	}

	public static VBO create(int type) {
		return new VBO(getFreeId(), type);
	}

	private static int getFreeId(){
		int[] idBuffer = new int[1];
		GLES20.glGenBuffers(1, idBuffer, 0);
		return idBuffer[0];
	}

	public void bind(){
		GLES20.glBindBuffer(type, id);
	}

	public void unbind(){
		GLES20.glBindBuffer(type, 0);
	}

	public void storeData(int[] data){
		IntBuffer buffer = Buffers.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		storeData(Buffers.bytesIn(buffer), buffer);
	}

	public void storeData(int length, IntBuffer buffer){
		GLES20.glBufferData(type, length, buffer, GLES20.GL_STATIC_DRAW);
	}

	public void storeData(float[] data){
		FloatBuffer buffer = Buffers.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		storeData(Buffers.bytesIn(buffer), buffer);
	}

	public void storeData(int length, FloatBuffer buffer){
		GLES20.glBufferData(type, length, buffer, GLES20.GL_STATIC_DRAW);
	}

	public void delete(){
		GLES20.glDeleteBuffers(1, new int[]{id}, 0);
	}
}
