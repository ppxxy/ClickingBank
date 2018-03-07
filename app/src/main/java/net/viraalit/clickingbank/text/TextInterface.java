package net.viraalit.clickingbank.text;

import android.opengl.GLES20;

import net.viraalit.clickingbank.models.AttributeVBO;
import net.viraalit.clickingbank.tools.Vector3f;

/**
 * Created by Kim on 1.3.2018.
 */

public class TextInterface {

	private float fontSize;
	private String text;

	private int vertexCount;
	private Vector3f colour = new Vector3f(0f, 0f, 0f);

	private AttributeVBO vertexVBO;
	private AttributeVBO textureCoordVBO;

	private Font font;

	public TextInterface(Font font, String text, float fontSize, Vector3f colour){
		this.font = font;
		this.text = text;
		this.fontSize = fontSize;
		this.colour = colour;
		setTextMeshData(font.generateMesh(this));
	}

	public String getText(){
		return this.text;
	}

	public float getFontSize(){
		return this.fontSize;
	}

	public Font getFont(){
		return this.font;
	}

	public void setTextMeshData(TextMeshData meshData){
		vertexVBO = AttributeVBO.create(GLES20.GL_ARRAY_BUFFER, 2, GLES20.GL_FLOAT);
		vertexVBO.bind();
		vertexVBO.storeData(meshData.getVertexPositions());
		vertexVBO.unbind();

		vertexCount = meshData.getVertexCount();

		textureCoordVBO = AttributeVBO.create(GLES20.GL_ARRAY_BUFFER, 2, GLES20.GL_FLOAT);
		textureCoordVBO.bind();
		textureCoordVBO.storeData(meshData.getTextureCoords());
		textureCoordVBO.unbind();
	}

	public void cleanUp(){
		vertexVBO.delete();
		textureCoordVBO.delete();
	}

	public void bindBuffers(){
		vertexVBO.bind();
		GLES20.glEnableVertexAttribArray(0);
		vertexVBO.setPointer(0);

		textureCoordVBO.bind();
		GLES20.glEnableVertexAttribArray(1);
		textureCoordVBO.setPointer(1);
	}

	public Vector3f getColour(){
		return this.colour;
	}

	public int getVertexCount(){
		return this.vertexCount;
	}

	public void unbindBuffers(){
		vertexVBO.unbind();
		GLES20.glDisableVertexAttribArray(0);

		textureCoordVBO.unbind();
		GLES20.glDisableVertexAttribArray(1);
	}
}
