package dev.johnnyleek.engine.sprite;

import java.util.UUID;

/**
 * Represents a drawable element.
 * 
 * A drawable element can either be a general game object (a shape, image, text, etc.)
 * or a UI element.
 * 
 * UI elements are intended to be rendered at the top (that is, above all game elements), but
 * they are still drawable. 
 * 
 * Drawable elements are provided with a unique ID (UUID) in order to be referenced by the window
 * and renderer later so they can be modified without having to destroy and recreate the element
 * each time. IDs CANNOT be set, they are generated randomly at instantiation and are final.
 * 
 * Drawable elements can also be given a unique name so that they can be found easily 
 * (using the window.getElementByName() method).
 * 
 * The position of each element is also tracked so that it can be rendered in the appropriate position.
 * 
 * A custom name can also be provided to the element for easy finding and access of elements.
 * 
 * @author Johnny Leek
 * @version 1.0
 *
 */
public abstract class Drawable {

	private String name;
	private final UUID ELEMENT_ID;
	private int posX, posY;
	
	/**
	 * Creates a drawable element with a position, and generates a unique ID
	 * @param posX - the X position of the element
	 * @param posY - the Y position of the element
	 */
	public Drawable(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		this.ELEMENT_ID = UUID.randomUUID();
	}
	
	/**
	 * Creates a drawable element with a position and a name, and generates a unique ID
	 * @param posX - the X position of the element
	 * @param posY - the Y position of the element
	 * @param name - the name of the element
	 */
	public Drawable(int posX, int posY, String name) {
		this.posX = posX;
		this.posY = posY;
		this.name = name;
		this.ELEMENT_ID = UUID.randomUUID();
	}

	/**
	 * Gets the name of the element
	 * @return the name of the element
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the element
	 * @param name - the new name of the element
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the ID of the element
	 * @return the ID of the element
	 */
	public UUID getID() {
		return ELEMENT_ID;
	}

	/**
	 * Gets the X position of the element
	 * @return the X position of the element
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * Sets the X position of the element
	 * @param posX - the new X position of the element
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}

	/**
	 * Gets the Y position of the element
	 * @return the Y position of the element
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * Sets the Y position of the element
	 * @param posY - the new Y position of the element
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	
	
	
}
