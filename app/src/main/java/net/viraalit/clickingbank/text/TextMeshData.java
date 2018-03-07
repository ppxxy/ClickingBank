package net.viraalit.clickingbank.text;

/**
 * Created by Kim on 1.3.2018.
 */

public class TextMeshData {

	private float[] vertexPositions;
	private float[] textureCoords;

	protected TextMeshData(float[] vertexPositions, float[] textureCoords){
		this.vertexPositions = vertexPositions;
		this.textureCoords = textureCoords;
	}

	public float[] getVertexPositions(){
		return this.vertexPositions;
	}

	public float[] getTextureCoords(){
		return this.textureCoords;
	}

	public int getVertexCount(){
		return this.vertexPositions.length/2;
	}
}
