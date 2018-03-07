package net.viraalit.clickingbank.entities;

import net.viraalit.clickingbank.models.Model;
import net.viraalit.clickingbank.tools.LWJGLMatrix4f;
import net.viraalit.clickingbank.tools.Vector3f;

/**
 * Created by Kim on 11.1.2018.
 */

public class Item {

	private Model model;
	private Vector3f position;
	private Vector3f rotation;

	private LWJGLMatrix4f transformationMatrix;

	public Item(Model model, Vector3f position, Vector3f rotation){
		this.model = model;
		this.position = position;
		this.rotation = rotation;
		loadTransformationMatrix();
	}

	public LWJGLMatrix4f loadTransformationMatrix(){
		LWJGLMatrix4f temp = new LWJGLMatrix4f();
		temp.translate(position);
		if(rotation != null) {
			temp.rotate(rotation.x, new Vector3f(1, 0, 0));
			temp.rotate(rotation.y, new Vector3f(0, 1, 0));
			temp.rotate(rotation.z, new Vector3f(0, 0, 1));
		}
		temp.scale(new Vector3f(1f, 1f, 1f));
		return this.transformationMatrix = temp;
	}

	public Vector3f getPosition(){
		return this.position;
	}

	public LWJGLMatrix4f getTransformationMatrix(){
		return this.transformationMatrix;
	}

	public Model getModel(){
		return this.model;
	}
}
