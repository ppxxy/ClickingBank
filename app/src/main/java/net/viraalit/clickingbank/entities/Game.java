package net.viraalit.clickingbank.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;

import net.viraalit.clickingbank.R;
import net.viraalit.clickingbank.models.Model;
import net.viraalit.clickingbank.models.Texture;
import net.viraalit.clickingbank.models.TexturedModel;
import net.viraalit.clickingbank.models.WavefrontLoader;
import net.viraalit.clickingbank.text.TextInterface;
import net.viraalit.clickingbank.text.ValueInterfaceGenerator;
import net.viraalit.clickingbank.tools.Vector3f;

import java.io.IOException;

/**
 * Created by Kim on 7.3.2018.
 */

public class Game {

	private Camera camera;
	private Item item;

	private GLSurfaceView queue;

	public static GameState state;
	private TextInterface valueInterface;

	public Game(Resources resources){
		try {
			Bitmap textureBitmap = BitmapFactory.decodeResource(resources, R.drawable.pouch_texture);
			Texture texture = new Texture.Loader().load(textureBitmap);
			Model model = new TexturedModel(new WavefrontLoader(resources.getAssets().open("models/pouch.obj")).loadToVao(), texture);
			item = new Item(model, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));
		} catch (IOException e) {
			e.printStackTrace();
		}

		createValueInterface();

		this.camera = new Camera.TargetCamera(new Vector3f(0, 2, 0));
	}

	public Camera getCamera(){
		return this.camera;
	}

	public void setRenderViewForQueue(final GLSurfaceView queue) {
		this.queue = queue;
	}

	public Item getItem() {
		return item;
	}

	public int getValue() {
		return state.getValue();
	}

	public void increaseValue(){
		this.state.increaseValue();
		updateValueInterface();
	}

	private void createValueInterface(){
		TextInterface valueInterface = ValueInterfaceGenerator.generateValueInterface(this.state.getValue());
		this.valueInterface = valueInterface;
	}

	private void updateValueInterface(){
		queue.queueEvent(new Runnable(){
			@Override
			public void run() {
				TextInterface inter = ValueInterfaceGenerator.generateValueInterface(state.getValue());
				valueInterface = inter;
			}
		});
	}

	public TextInterface getValueInterface() {
		return valueInterface;
	}
}
