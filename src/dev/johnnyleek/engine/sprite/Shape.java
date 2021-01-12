package dev.johnnyleek.engine.sprite;

import java.awt.Color;

/**
 * Represents a drawable shape.
 * 
 * Shapes can be one of a few Shape Types (specified in the ShapeType enum).
 * 
 * Shapes can be instantiated by providing a ShapeType, Color, width, height,
 * and position.
 * 
 * Shapes' color and ShapeType can both be changed using the "setColor" and 
 * "setShape" methods respectively.
 * 
 * @author Johnny leek
 * @version 1.0
 *
 */
public class Shape extends Sprite {

	private ShapeType shape;
	private Color color;
	
	/**
	 * Instantiates a new shape
	 * @param shape the ShapeType of what shape the Shape should be
	 * @param color the color of the shape
	 * @param width the width of the shape
	 * @param height the height of the shape
	 * @param posX the X position to render the shape
	 * @param posY the Y position to render the shape
	 */
	public Shape(ShapeType shape, Color color, int width, int height, int posX, int posY) {
		super(posX, posY, width, height);
		this.shape = shape;
		this.color = color;
	}
	
	/**
	 * Instantiates a new shape (with a name)
	 * @param shape the ShapeType of what shape the Shape should be
	 * @param color the color of the shape
	 * @param width the width of the shape
	 * @param height the height of the shape
	 * @param posX the X position to render the shape
	 * @param posY the Y position to render the shape
	 * @param name the name of the shape (should be unique)
	 */
	public Shape(ShapeType shape, Color color, int width, int height, int posX, int posY, String name) {
		super(posX, posY, width, height, name);
		this.shape = shape;
		this.color = color;
	}
	
	/**
	 * Gets the color of the shape
	 * @return the color of the shape
	 */
	public Color getColor() {
		return this.color;
	}
	
	/**
	 * Sets the color of the shape
	 * @param color the new color of the shape
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Gets the current ShapeType
	 * @return the current ShapeType
	 */
	public ShapeType getShape() {
		return this.shape;
	}
	
	/**
	 * Sets the ShapeType of the shape
	 * @param shape the new ShapeType
	 */
	public void setShape(ShapeType shape) {
		this.shape = shape;
	}
	
}
