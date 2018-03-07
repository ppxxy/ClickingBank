package net.viraalit.clickingbank.entities;

import net.viraalit.clickingbank.tools.LWJGLMatrix4f;
import net.viraalit.clickingbank.tools.Vector3f;

/**
 * Created by Kim on 26.12.2017.
 */

public class Camera {

	protected Vector3f position;
	protected float pitch = 90f, yaw = 90f, roll = 0f;

	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.5f, FAR_PLANE = 1000.0f;

	private LWJGLMatrix4f projectionMatrix = new LWJGLMatrix4f();
	private LWJGLMatrix4f viewMatrix = new LWJGLMatrix4f();

	public Camera(){
		this.position = new Vector3f(10f, 25f, 5f);
		updateViewMatrix();
	}

	public Camera(Vector3f position){
		this.position = position;
		updateViewMatrix();
	}

	public LWJGLMatrix4f updateProjectionMatrix(int width, int height) {
		projectionMatrix = new LWJGLMatrix4f();
		float aspectRatio = (float) width / (float) height;
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
		return projectionMatrix;
	}

	protected void updateViewMatrix() {
		viewMatrix.setIdentity();
		LWJGLMatrix4f.rotate((float) Math.toRadians(pitch), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
		LWJGLMatrix4f.rotate((float) Math.toRadians(yaw), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		Vector3f negativeCameraPos = new Vector3f(-position.x, -position.y, -position.z);
		LWJGLMatrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
	}

	public LWJGLMatrix4f getProjectionViewMatrix(){
		return LWJGLMatrix4f.mul(projectionMatrix, viewMatrix, null);
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position){
		this.position = position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

	public LWJGLMatrix4f getProjectionMatrix() {
		return this.projectionMatrix;
	}

	public LWJGLMatrix4f getViewMatrix(){
		return this.viewMatrix;
	}

	public float getFOV() {
		return (float) Math.toRadians(FOV/2);
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
		updateViewMatrix();
	}

	public Vector3f getPointOnRay(Vector3f ray, float distance) {
		Vector3f start = new Vector3f(position.x, position.y, position.z);
		Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
		return Vector3f.add(start, scaledRay, null);
	}

	public void changeDistance(float change) {
		float distance = position.length()+change/100;
		position.normalise();
		this.position = new Vector3f(position.x * distance, position.y * distance, position.z * distance);
		updateViewMatrix();
	}

	public static class TargetCamera extends Camera{

		private Vector3f targetPosition;
		private float distance = 5f;

		public TargetCamera(Vector3f targetPosition){
			super();
			this.targetPosition = targetPosition;
			rotate(new Vector3f(0f, 0f, 0f));
		}

		public void rotate(Vector3f rotation){
			float y = (float) Math.sin(Math.toRadians(rotation.getX()));
			float xz = (float) Math.cos(Math.toRadians(rotation.getX()));
			float x = xz * (float) Math.sin(Math.toRadians(rotation.getY()));
			float z = xz * (float) Math.cos(Math.toRadians(rotation.getY()));
			Vector3f newPosition = new Vector3f(x, y, z);
			newPosition.multiply(distance);
			newPosition.add(targetPosition);
			this.position = newPosition;
			//System.out.println(newPosition.toString());
			this.pitch = rotation.getX();
			this.yaw = rotation.getY();
			//this.position = newPosition;
			updateViewMatrix();
		}

		public void zoomAndRotate(float distance, Vector3f rotation) {
			float y = (float) Math.sin(Math.toRadians(rotation.getX()));
			float xz = (float) Math.cos(Math.toRadians(rotation.getX()));
			float x = xz * (float) -Math.sin(Math.toRadians(rotation.getY()));
			float z = xz * (float) Math.cos(Math.toRadians(rotation.getY()));
			Vector3f newPosition = new Vector3f(x, y, z);
			newPosition.multiply(this.distance);
			newPosition.add(targetPosition);
			this.distance = distance;
			this.pitch = rotation.getX();
			this.yaw = rotation.getY();
			this.position = newPosition;
			updateViewMatrix();
		}

		public void zoom(float distance){
			Vector3f newPosition = new Vector3f(this.position.x - targetPosition.x, this.position.y - targetPosition.y, this.position.z - targetPosition.z);
			newPosition.normalise();
			newPosition.multiply(distance);
			newPosition.add(targetPosition);
			this.position = newPosition;
			updateViewMatrix();
			this.distance = distance;
		}

		public float getDistance() {
			return distance;
		}
	}
}
