package net.viraalit.clickingbank.rendering;

import android.view.View;

import net.viraalit.clickingbank.MainView;

/**
 * Created by Kim on 7.3.2018.
 */

public abstract class MasterRenderer implements View.OnTouchListener{

	protected MainView mainView;

	protected MasterRenderer(MainView mainView){
		this.mainView = mainView;
	}

	public abstract void render();
}
