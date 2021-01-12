package dev.johnnyleek.engine.input;

/**
 * Provides customizable key bindings for common game controls.
 * Each binding has a name (Ex: "UP"), and a corresponding Key Code (Ex: 87)
 * 
 * Keycodes can be found at <a href="https://docs.oracle.com/javase/7/docs/api/constant-values.html#java.awt.event.KeyEvent.CHAR_UNDEFINED">javadoc</a>
 * 
 * @author Johnny Leek
 * @version 1.0
 */
public enum KeyBinding {

	UP(87),
	DOWN(83),
	LEFT(65),
	RIGHT(68);
	
	private int keyCode;

	private KeyBinding(int keyCode) {
		this.keyCode = keyCode;
	}
	
	/**
	 * Gets the keycode for the given key
	 * @return the keycode for the corresponding key
	 */
	public int getKeyCode() {
		return this.keyCode;
	}
	
	/**
	 * Sets the keycode for the given key.
	 * This allows for keys to be rebound as necessary
	 * @param keyCode the keycode (as an integer) to change the keybinding to
	 */
	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}	
	
}
