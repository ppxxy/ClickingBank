package net.viraalit.clickingbank.rendering;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.view.MotionEvent;
import android.view.View;

import net.viraalit.clickingbank.MainView;
import net.viraalit.clickingbank.entities.Camera;
import net.viraalit.clickingbank.entities.Game;
import net.viraalit.clickingbank.skybox.SkyboxRenderer;
import net.viraalit.clickingbank.text.ValueInterfaceGenerator;
import net.viraalit.clickingbank.text.ValueRenderer;
import net.viraalit.clickingbank.tools.Vector2f;

/**
 * Created by Kim on 23.12.2017.
 */

public class GameRenderer extends MasterRenderer{

	private ObjectRenderer objectRenderer;
	private ValueRenderer valueRenderer;
	private SkyboxRenderer skyboxRenderer;

	private SavedTouchAction<?> savedTouchAction;

	private Game game;

	public GameRenderer(MainView view, Resources resources) {
		super(view);
		ValueInterfaceGenerator.initialize(resources);
		game = new Game(resources);
		game.setRenderViewForQueue(view);
		GLES20.glEnable(GLES20.GL_CULL_FACE);
		GLES20.glCullFace(GLES20.GL_BACK);
		objectRenderer = new ObjectRenderer(resources.getAssets());
		valueRenderer = new ValueRenderer(resources.getAssets());
		skyboxRenderer = new SkyboxRenderer(resources);
	}

	@Override
	public void render() {
		skyboxRenderer.render(game.getCamera());
		objectRenderer.render(game.getCamera(), game.getItem());
		valueRenderer.render(game.getValueInterface());
	}

	public Game getGame(){
		return this.game;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getAction() & MotionEvent.ACTION_MASK){
			case MotionEvent.ACTION_DOWN:
				game.increaseValue();
				return true;
			case MotionEvent.ACTION_POINTER_DOWN:
				game.increaseValue();
				return true;
		}
		return false;
	}

	private float getDistance(MotionEvent e){
		MotionEvent.PointerCoords coords0 = new MotionEvent.PointerCoords();
		MotionEvent.PointerCoords coords1 = new MotionEvent.PointerCoords();
		e.getPointerCoords(0, coords0);
		e.getPointerCoords(1, coords1);
		return (float) Math.hypot(coords0.x-coords1.x, coords0.y-coords1.y);
	}

	private Vector2f getCenterCoords(MotionEvent e){
		int amount = e.getPointerCount();
		float x = 0, y = 0;
		for(int i = 0; i < amount; i++){
			x += e.getX(i);
			y += e.getY(i);
		}
		return new Vector2f(x/amount, y/amount);
	}

	public Camera getCamera(){
		return this.game.getCamera();
	}

	private class SavedTouchAction<T>{

		private static final int ZOOM_ACTION = 1, MOVE_ACTION = 2;

		private int actionType;
		private T[] values;

		private SavedTouchAction(int type, T... values){
			this.actionType = type;
			this.values = values;
		}

		private T getValue(int index){
			return this.values[index];
		}

		private void setValue(T value, int index){
			this.values[index] = value;
		}

		private int getActionType(){
			return this.actionType;
		}
	}
}
