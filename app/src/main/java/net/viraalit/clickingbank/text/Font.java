package net.viraalit.clickingbank.text;

import net.viraalit.clickingbank.models.Texture;

import java.io.InputStream;

/**
 * Created by Kim on 1.3.2018.
 */

public class Font {

	private Texture textureAtlas;
	private TextMeshCreator loader;

	public Font(Texture textureAtlas, InputStream textDataSource){
		this.textureAtlas = textureAtlas;
		this.loader = new TextMeshCreator(textDataSource);
	}

	public Texture getTextureAtlas(){
		return this.textureAtlas;
	}

	public TextMeshData generateMesh(TextInterface textInterface){
		return loader.createTextMesh(textInterface);
	}

}
