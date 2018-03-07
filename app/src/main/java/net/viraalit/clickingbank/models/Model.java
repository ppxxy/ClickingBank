package net.viraalit.clickingbank.models;

/**
 * Created by Kim on 11.1.2018.
 */

public class Model {

	public static final int TYPE_COLORED = 0, TYPE_TEXTURED = 1;

	private int type;
	protected VAO vao;

	protected Model(int type, VAO vao){
		this.type = type;
		this.vao = vao;
	}

	public VAO getVao(){
		return this.vao;
	}

	public int getType(){
		return this.type;
	}
}
