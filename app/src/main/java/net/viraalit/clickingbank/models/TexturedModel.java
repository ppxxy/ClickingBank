package net.viraalit.clickingbank.models;

/**
 * Created by Kim on 11.1.2018.
 */

public class TexturedModel extends Model {

	private Texture texture;

	public TexturedModel(VAO vao, Texture texture) {
		super(Model.TYPE_TEXTURED, vao);
		this.texture = texture;
	}

	public Texture getTexture(){
		return this.texture;
	}
}
