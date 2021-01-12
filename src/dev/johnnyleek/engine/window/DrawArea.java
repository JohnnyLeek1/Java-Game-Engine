package dev.johnnyleek.engine.window;

import java.awt.Canvas;

/**
 * Serves as a container for the drawable canvas.
 * 
 * A DrawArea keeps track of it's ability to be drawn to using the
 * "drawable" variable.
 * 
 * @author Johnny Leek
 * @version 1.0
 *
 */
public class DrawArea {

	private Canvas canvas;
	private boolean drawable;
	
	/**
	 * Instantiates a new DrawArea and creates a new Canvas
	 */
	public DrawArea() {
		this.canvas = new Canvas();		
		this.drawable = true;
	}

	/**
	 * Gets the Canvas
	 * @return the Canvas of the DrawArea
	 */
	public Canvas getArea() {
		return this.canvas;
	}
	
	/**
	 * Returns whether or not this Canvas can be drawn on
	 * @return true/false depending on if the Canvas can be drawn on
	 */
	public boolean isDrawable() {
		return drawable;
	}

	/**
	 * Sets this Canvas' ability to be drawn on
	 * @param drawable whether or not this Canvas can be drawn on
	 */
	public void setDrawable(boolean drawable) {
		this.drawable = drawable;
	}	
	
}
