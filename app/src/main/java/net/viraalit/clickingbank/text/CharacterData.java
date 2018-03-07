package net.viraalit.clickingbank.text;

/**
 * Created by Kim on 1.3.2018.
 * One possibility to improve this would be to store {@code width}, {@code height}, {@code xOffset}, {@code yOffset} and {@code xAdvance} as floats and
 * calculating perPixelSize in that value at initialization. This value however should be updated every time the device's orientation and/or dimensions change.
 *
 * To make this possible, every characterData should have it's corresponding int values stored and every CharacterData currently in use should be stored in some array, updating
 * each element in that array when device dimensions change.
 */

public class CharacterData {

	/**
	 * ASCII-keycode of the character.
	 */
	private int id;
	/**
	 * Character's x coordinate in relation to atlas' width. This value is passed to shader.
	 */
	private float xTextureCoord;
	/**
	 * Character's y coordinate in relation to atlas' height. This value is passed to shader.
	 */
	private float yTextureCoord;

	/**
	 * Character's width in relation to atlas' width. This value is passed to shader.
	 */
	private float textureWidth;
	/**
	 * Character's height in relation to atlas' height. This value is passed to shader.
	 */
	private float textureHeight;
	/**
	 * Character's width in pixels. This value is used to calculate the mesh size.
	 */
	private int width;
	/**
	 * Character's height in pixels. This value is used to calculate the mesh size.
	 */
	private int height;
	/**
	 * Character's offset in pixels. This value is used to calculate the offset when creating mesh.
	 */
	private int xOffset;
	/**
	 * Character's offset in pixels. This value is used to calculate the offset when creating mesh.
	 */
	private int yOffset;
	/**
	 * The amount of pixels that the pointer should move to the right after this character.
	 */
	private int xAdvance;

	protected CharacterData(int id, float xTextureCoord, float yTextureCoord, float textureWidth, float textureHeight, int width, int height, int xOffset, int yOffset, int xAdvance){
		this.id = id;
		this.xTextureCoord = xTextureCoord;
		this.yTextureCoord = yTextureCoord;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.width = width;
		this.height = height;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.xAdvance = xAdvance;
	}

	public int getId() {
		return id;
	}

	public float getxTextureCoord() {
		return xTextureCoord;
	}

	public float getyTextureCoord() {
		return yTextureCoord;
	}

	public float getTextureWidth() {
		return textureWidth;
	}

	public float getTextureHeight() {
		return textureHeight;
	}

	public float getQuadWidth(float horizontalPerPixelSize) {
		return width * horizontalPerPixelSize;
	}

	public float getQuadHeight(float verticalPerPixelSize) {
		return height * verticalPerPixelSize;
	}

	public float getxOffset(float horizontalPerPixelSize) {
		return xOffset * horizontalPerPixelSize;
	}

	public float getyOffset(float verticalPerPixelSize) {
		return yOffset * verticalPerPixelSize;
	}

	public float getxAdvance(float horizontalPerPixelSize) {
		return xAdvance * horizontalPerPixelSize;
	}

	public int getAdvance() {
		return xAdvance;
	}
}
