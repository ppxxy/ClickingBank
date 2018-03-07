package net.viraalit.clickingbank.tools;

/**
 * Created by Kim on 19.6.2017.
 */

public class Vector2f {

    public float x, y;

    public Vector2f(float x, float y){
        this.x = x;
        this.y = y;
    }

	public float getValue(int i){
		switch(i){
			case 0:
				return x;
			case 1:
				return y;
		}
		throw new IllegalArgumentException("Vector2f only has 2 dimensions. Only valid values are 0 and 1.");
	}

	public Vector2f copy() {
		return new Vector2f(x, y);
	}
}
