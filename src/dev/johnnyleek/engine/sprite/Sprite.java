package dev.johnnyleek.engine.sprite;

import java.awt.Dimension;
import java.util.UUID;

/**
 * Contains logic for anything that is rendered onto the screen.
 * 
 * Sprite's are abstract, thus they cannot be instantiated.
 * Instead, you must instantiate one of its implementations (such as
 * Image or Shape).
 * 
 * The only field unique to a sprite is its size. This is represented
 * by a "Dimension", which contains width and height attributes.
 * 
 * @author Johnny Leek
 * @version 1.0
 *
 */
public abstract class Sprite extends Drawable{

	private Dimension size;

	/**
	 * Instantiates a sprite at a position with a width and height
	 * @param posX the X position to render the sprite
	 * @param posY the Y position to render the sprite
	 * @param width the width of the sprite
	 * @param height the height of the sprite
	 */
	public Sprite(int posX, int posY, int width, int height) {
		super(posX, posY);
		this.size = new Dimension(width, height);
	}
	
	/**
	 * Instantiates a sprite at a position
	 * @param posX the X position to render the sprite
	 * @param posY the Y position to render the sprite
	 */
	public Sprite(int posX, int posY) {
		super(posX, posY);
	}
	
	/**
	 * Instantiates a sprite at a position with a width and height, with a name (as an identifier)
	 * @param posX the X position to render the sprite
	 * @param posY the Y position to render the sprite
	 * @param width the width of the sprite
	 * @param height the height of the sprite
	 * @param name the name to identify the sprite
	 */
	public Sprite(int posX, int posY, int width, int height, String name) {
		super(posX, posY, name);
		this.size = new Dimension(width, height);
	}
	
	/**
	 * Instantiates a sprite at a position with a name (as an identifier)
	 * @param posX the X position to render the sprite
	 * @param posY the Y position to render the sprite
	 * @param name the name to identify the sprite
	 */
	public Sprite(int posX, int posY, String name) {
		super(posX, posY, name);
	}
	
	/**
	 * Returns the total size of the sprite (as a Dimension)
	 * @return the size of the sprite as a Dimension
	 */
	public Dimension getSize() {
		return size;
	}
	
	/**
	 * Returns the width of the sprite
	 * @return the width of the sprite
	 */
	public int getWidth() {
		return this.size.width;
	}
	
	/**
	 * Returns the height of the sprite
	 * @return the height of the sprite
	 */
	public int getHeight() {
		return this.size.height;
	}
	
	/**
	 * Sets the size of the sprite via a Dimension
	 * @param size the new size of the sprite
	 */
	public void setSize(Dimension size) {
		this.size = size;
	}
	
	/**
	 * Sets the size of the sprite using a width and height
	 * @param width the new width of the sprite
	 * @param height the new height of the sprite
	 */
	public void setSize(int width, int height) {
		this.size = new Dimension(width, height);
	}
}
