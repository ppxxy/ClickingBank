package net.viraalit.clickingbank.models;

import android.opengl.GLES20;

import java.util.HashMap;

/**
 * Created by Kim on 7.3.2018.
 */

public class VAO {

	private HashMap<Integer, AttributeVBO> vbos = new HashMap<>();
	private VBO indexVBO;
	private int indexCount;

	private VAO(){

	}

	public static VAO create(){
		return new VAO();
	}

	public int getIndexCount(){
		return this.indexCount;
	}

	public void createIndexBuffer(int[] indices){
		this.indexVBO = VBO.create(GLES20.GL_ELEMENT_ARRAY_BUFFER);
		indexVBO.bind();
		indexVBO.storeData(indices);
		indexVBO.unbind();
		this.indexCount = indices.length;
	}

	public void createAttribute(int attribute, float[] data, int attrSize){
		AttributeVBO vbo = AttributeVBO.create(GLES20.GL_ARRAY_BUFFER, attrSize, GLES20.GL_FLOAT);
		vbo.bind();
		vbo.storeData(data);
		vbo.unbind();
		vbos.put(attribute, vbo);
	}

	public void createAttribute(int attribute, int[] data, int attrSize){
		AttributeVBO vbo = AttributeVBO.create(GLES20.GL_ARRAY_BUFFER, attrSize, GLES20.GL_FLOAT);
		vbo.bind();
		vbo.storeData(data);
		vbo.unbind();
		vbos.put(attribute, vbo);
	}

	public void bind(int... attributes){
		if(indexVBO != null){
			indexVBO.bind();
		}
		for(int i : attributes){
			vbos.get(i).bind();
			GLES20.glEnableVertexAttribArray(i);
			vbos.get(i).setPointer(i);
		}
	}

	public void bindWithPointer(int[] attributes, int[] targets){
		if(indexVBO != null){
			indexVBO.bind();
		}
		for(int i = 0; i < attributes.length; i++){
			vbos.get(attributes[i]).bind();
			GLES20.glEnableVertexAttribArray(attributes[i]);
			vbos.get(attributes[i]).setPointer(targets[i]);
		}
	}

	public void unbind(int... attributes){
		if(indexVBO != null){
			indexVBO.unbind();
		}
		for(int i : attributes){
			vbos.get(i).unbind();
			GLES20.glDisableVertexAttribArray(i);
		}
	}

	public void delete(){
		for(VBO vbo : this.vbos.values()){
			vbo.delete();
		}
		indexVBO.delete();
	}
}
