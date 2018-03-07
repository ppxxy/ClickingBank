package net.viraalit.clickingbank.models;

import android.opengl.GLES20;

/**
 * Created by Kim on 19.12.2017.
 */

public class AttributeVBO extends VBO {

	private int attrSize, dataType;

	private AttributeVBO(int type, int attrSize, int dataType) {
		super(type);
		this.attrSize = attrSize;
		this.dataType = dataType;
	}

	public static AttributeVBO create(int type, int attrSize, int dataType){
		return new AttributeVBO(type, attrSize, dataType);
	}

	public void setPointer(int attribute){
		GLES20.glVertexAttribPointer(attribute, attrSize, dataType, false, 0, 0);
	}
}
