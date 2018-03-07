package net.viraalit.clickingbank;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.viraalit.clickingbank.entities.Game;
import net.viraalit.clickingbank.entities.GameState;

public class MainActivity extends Activity {

	private GLSurfaceView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.view = new MainView(this);
		setContentView(view);
		Game.state = new GameState();
	}

	@Override
	protected void onStop(){
		super.onStop();
		Game.state.saveState(this.getFilesDir());
		this.view.onPause();
	}

	@Override
	protected void onStart(){
		super.onStart();
		GameState.loadState(this.getFilesDir());
		this.view.onResume();
	}
}
