package net.viraalit.clickingbank.text;

import net.viraalit.clickingbank.tools.Settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kim on 1.3.2018.
 */

public class MeshDataLoader {

	private Map<Integer, CharacterData> characters = new HashMap<Integer, CharacterData>();

	public MeshDataLoader(InputStream stream){
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder jsonBuilder = new StringBuilder();
		try{
			String line;
			while((line = reader.readLine()) != null){
				jsonBuilder.append(line +'\n');
			}
			JSONObject charactersArray = new JSONObject(jsonBuilder.toString());
			int imageSize = charactersArray.getInt("scale");
			int lineHeightInPixels = charactersArray.getInt("line_height");
			Settings.setLineHeightInPixels(lineHeightInPixels);
			JSONArray array = charactersArray.getJSONArray("characters");
			for(int i = 0; i < array.length(); i++){
				handleCharacter(array.getJSONObject(i), imageSize);
			}
		} catch(IOException e){
			e.printStackTrace();
		} catch(JSONException e){
			e.printStackTrace();
		} finally {
			try{
				reader.close();
				stream.close();
			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	public CharacterData getCharacterData(int id){
		return characters.get(id);
	}

	private void handleCharacter(JSONObject characterData, int imageSize) throws JSONException {
		boolean multipleIds = characterData.has("ids");
		float x = (float) characterData.getInt("x")/imageSize;
		float y = (float) characterData.getInt("y")/imageSize;
		int width = characterData.getInt("width");
		int height = characterData.getInt("height");
		float texWidth = (float) width / imageSize;
		float texHeight = (float) height / imageSize;
		int xOffset = characterData.getInt("xoffset");
		int yOffset = characterData.getInt("yoffset");
		int xAdvance = characterData.getInt("xadvance");
		if(multipleIds){
			JSONArray ids = characterData.getJSONArray("ids");
			for(int i = 0; i < ids.length(); i++){
				int id = ids.getInt(i);
				CharacterData character = new CharacterData(id, x, y, texWidth, texHeight, width, height, xOffset, yOffset, xAdvance);
				characters.put(character.getId(), character);
			}
		}
		else{
			int id = characterData.getInt("id");
			CharacterData character = new CharacterData(id, x, y, texWidth, texHeight, width, height, xOffset, yOffset, xAdvance);
			characters.put(character.getId(), character);
		}
	}
}
