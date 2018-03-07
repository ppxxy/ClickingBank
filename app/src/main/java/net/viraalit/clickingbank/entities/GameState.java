package net.viraalit.clickingbank.entities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Kim on 7.3.2018.
 */

public class GameState implements Serializable{

	private static final String FILENAME = "game_save";
	private int value;

	public int getValue() {
		return value;
	}

	public void increaseValue() {
		value++;
	}

	public void saveState(File filesDir){
		File saveFile = new File(filesDir, FILENAME);
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(saveFile));
			oos.writeObject(this);
			oos.flush();
		} catch(IOException e){
			e.printStackTrace();
		} finally{
			if(oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void loadState(File filesDir) {
		File saveFile = new File(filesDir, FILENAME);
		ObjectInputStream oos = null;
		try {
			oos = new ObjectInputStream(new FileInputStream(saveFile));
			GameState state = (GameState) oos.readObject();
			Game.state = state;
		} catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally{
			if(oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
