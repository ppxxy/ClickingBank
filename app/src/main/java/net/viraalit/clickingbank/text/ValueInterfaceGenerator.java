package net.viraalit.clickingbank.text;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import net.viraalit.clickingbank.R;
import net.viraalit.clickingbank.models.Texture;
import net.viraalit.clickingbank.tools.Vector3f;

/**
 * Created by Kim on 2.3.2018.
 */

public class ValueInterfaceGenerator {

	public static ValueInterfaceGenerator generator;

	private Font font;

	private ValueInterfaceGenerator(Resources resources){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = false;
		Bitmap fontAtlas = BitmapFactory.decodeResource(resources, R.drawable.numb64, options);
		font = new Font(new Texture.Loader().nearestFiltering().load(fontAtlas), resources.openRawResource(R.raw.numbersdata));
	}

	public static void initialize(Resources resources){
		generator = new ValueInterfaceGenerator(resources);
	}

	public static TextInterface generateValueInterface(int value){
		String amount = String.valueOf(value);
		return new TextInterface(generator.font, amount, 1f, new Vector3f(0f, 1f, 0f));
	}

	public static Font getFont(){
		return generator.font;
	}
}
