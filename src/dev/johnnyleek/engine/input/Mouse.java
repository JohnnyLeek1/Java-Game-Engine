package dev.johnnyleek.engine.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import dev.johnnyleek.engine.util.Logger;
import dev.johnnyleek.engine.window.Window;

/**
 * Provides a custom listener to keep track of the mouse position, as well as
 * whether or not the mouse is pressed.
 * 
 * @author Johnny Leek
 * @version 1.0
 *
 */
public class Mouse implements MouseMotionListener, java.awt.event.MouseListener {
	
	private static boolean isPressed = false;
	private static int mouseX = 0;
	private static int mouseY = 0;
	
	/**
	 * Returns whether or not the mouse is currently pressed.
	 * @return true/false depending on if the mouse is pressed
	 */
	public static boolean isPressed() {
		synchronized(Mouse.class) {
			return isPressed;
		}
	}
	
	/**
	 * Returns the current X coordinate of the mouse (relative to the game window).
	 * The LEFT of the window is 0
	 * @return the X coordinate of the mouse
	 */
	public static int getMouseX() {
		synchronized(Mouse.class) {
			return mouseX;
		}
	}
	
	/**
	 * Returns the current Y coordinate of the mouse (relative to the game window).
	 * The TOP of the window is 0
	 * @return the Y coordinate of the mouse
	 */
	public static int getMouseY() {
		synchronized(Mouse.class) {
			return mouseY;
		}
	}
	
	public Mouse() {
		Logger.info("New Mouse created!");
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// N/A
	}
	
	/**
	 * Fires whenever the mouse is pressed, and sets "isPressed" to true
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		isPressed = true;		
	}

	/**
	 * Fires whenever the mouse is released, and sets "isPressed" to false 
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		isPressed = false;		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// N/A		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// N/A
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 * Fires whenever the mouse is moved, and updates the X and Y position
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
}
