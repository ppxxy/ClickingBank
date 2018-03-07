package net.viraalit.clickingbank.text;

import net.viraalit.clickingbank.tools.Settings;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kim on 25.2.2018.
 */

public class TextMeshCreator {

	private MeshDataLoader loader;

	protected TextMeshCreator(InputStream stream){
		loader = new MeshDataLoader(stream);
	}

	protected TextMeshData createTextMesh(TextInterface textInterface){
		CharacterData[] characters = loadCharactersData(textInterface.getText());
		TextMeshData data = createQuadVertices(textInterface, characters);
		return data;
	}

	private CharacterData[] loadCharactersData(String text){
		char[] characterArray = text.toCharArray();
		CharacterData[] characters = new CharacterData[text.length()];
		for(int i = 0; i < characterArray.length; i++){
			CharacterData characterData = loader.getCharacterData((int) characterArray[i]);
			characters[i] = characterData;
		}
		return characters;
	}

	private TextMeshData createQuadVertices(TextInterface textInterface, CharacterData[] characters){
		float cursorX = 0f;
		List<Float> vertices = new ArrayList<Float>();
		List<Float> textureCoords = new ArrayList<Float>();
		for(CharacterData c : characters){
			addVerticesForCharacter(cursorX, c, textInterface.getFontSize(), vertices);
			addTexCoords(textureCoords, c.getxTextureCoord(), c.getyTextureCoord(), c.getTextureWidth(), c.getTextureHeight());
			cursorX += c.getxAdvance(Settings.horizontalPerPixelSize) * textInterface.getFontSize();
		}
		return new TextMeshData(listToArray(vertices), listToArray(textureCoords));
	}

	private static void addVerticesForCharacter(float cursorX, CharacterData character, float fontSize, List<Float> vertices){
		float x = cursorX + (character.getxOffset(Settings.horizontalPerPixelSize) * fontSize);
		float y = character.getyOffset(Settings.verticalPerPixelSize) * fontSize;
		float maxX = x + (character.getQuadWidth(Settings.horizontalPerPixelSize) * fontSize);
		float maxY = y + (character.getQuadHeight(Settings.verticalPerPixelSize) * fontSize);
		addVertices(vertices, 2f * x - 1f, -2f * y + 0.5f, 2f * maxX - 1f, -2f * maxY + 0.5f);
	}

	private static void addVertices(List<Float> vertices, float x, float y, float maxX, float maxY) {
		vertices.add(x);
		vertices.add(y);
		vertices.add(x);
		vertices.add(maxY);
		vertices.add(maxX);
		vertices.add(maxY);
		vertices.add(maxX);
		vertices.add(maxY);
		vertices.add(maxX);
		vertices.add(y);
		vertices.add(x);
		vertices.add(y);
	}

	private static void addTexCoords(List<Float> texCoords, float x, float y, float width, float height){
		float maxX = x + width;
		float maxY = y + height;
		texCoords.add(x);
		texCoords.add(y);
		texCoords.add(x);
		texCoords.add(maxY);
		texCoords.add(maxX);
		texCoords.add(maxY);
		texCoords.add(maxX);
		texCoords.add(maxY);
		texCoords.add(maxX);
		texCoords.add(y);
		texCoords.add(x);
		texCoords.add(y);
	}

	private static float[] listToArray(List<Float> listOfFloats){
		float[] array = new float[listOfFloats.size()];
		for(int i = 0; i < array.length; i++){
			array[i] = listOfFloats.get(i);
		}
		listOfFloats.clear();
		listOfFloats = null;
		return array;
	}
}
