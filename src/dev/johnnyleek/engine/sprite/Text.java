package dev.johnnyleek.engine.sprite;

import java.awt.Color;

/**
 * Represents renderable text.
 * 
 * This text will be rendered at the provided X and Y position,
 * and can be provided a Color (to change the color of the text).
 * 
 * @author Johnny Leek
 * @version 1.0
 *
 */
public class Text extends Drawable {

	private String text;
	private Color color;
	
	/**
	 * Instantiates text with the provided color at the X and Y position
	 * @param text the text to render
	 * @param color the color of the text
	 * @param posX the X position to render the text
	 * @param posY the Y position to render the text
	 */
	public Text(String text, Color color, int posX, int posY) {
		super(posX, posY);
		this.text = text;
		this.color = color;
	}
	
	/**
	 * Instantiates text with the provided color at the X and Y position, and a name (as an identifier)
	 * @param text the text to render
	 * @param color the color of the text
	 * @param posX the X position to render the text
	 * @param posY the Y position to render the text
	 * @param name the name to identify the sprite
	 */
	public Text(String text, Color color, int posX, int posY, String name) {
		super(posX, posY, name);
		this.text = text;
		this.color = color;
	}
	
	/**
	 * Gets the text as a String
	 * @return the contents of the text as a String
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Sets the text to be displayed
	 * @param text the new text to display
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Gets the current color of the text
	 * @return the color of the text
	 */
	public Color getColor() {
		return this.color;
	}
	
	/**
	 * Sets the color of the text to be displayed
	 * @param color the new color of the text
	 */
	public void setColor(Color color) {
		this.color = color;
	}
}
