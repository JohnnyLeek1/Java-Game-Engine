package dev.johnnyleek.engine.input;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import dev.johnnyleek.engine.util.Logger;

/**
 * Provides a custom dispatcher and map of which keys are pressed
 * in order to provide accurate information about the current key state
 * of the game.
 * 
 * @author Johnny Leek
 * @version 1.0
 *
 */
public class Keyboard {

	public Keyboard() {
		Logger.debug("New Keyboard created!");
		pressedKeys = new HashMap<Integer, Boolean>();
	}
	
	private static volatile HashMap<Integer, Boolean> pressedKeys;
	
	/**
	 * Returns whether or not a given key is pressed
	 * @param key a KeyBinding for a given key
	 * @return true/false depending on if the provided key is currently pressed
	 */
	public static boolean isPressed(KeyBinding key) {
		synchronized(Keyboard.class) {
			int keyCode = key.getKeyCode();
			if(pressedKeys.containsKey(keyCode))
				return pressedKeys.get(keyCode);
			else return false;
		}
	}
	
	/**
	 * Returns whether or not a given key is pressed
	 * @param key a keycode for a given key
	 * @return true/false depending on if the provided key is currently pressed
	 */
	public static boolean isPressed(int keyCode) {
		synchronized(Keyboard.class) {
			if(pressedKeys.containsKey(keyCode))
				return pressedKeys.get(keyCode);
			else return false;
		}
	}
	
	/**
	 * Custom event dispatcher that sets keys as pressed
	 */
	private KeyEventDispatcher keyDispatcher = new KeyEventDispatcher() {
		@Override
		public boolean dispatchKeyEvent(KeyEvent ke) {
			synchronized (Keyboard.class) {
                switch (ke.getID()) {
	                case KeyEvent.KEY_PRESSED:
	                    pressedKeys.put(ke.getKeyCode(), true);
	                    break;
	                case KeyEvent.KEY_RELEASED:
	                    pressedKeys.put(ke.getKeyCode(), false);
	                    break;
                }
                return false;
			}
		}
	};
	
	/**
	 * Returns the key dispatcher
	 * @return the key event dispatcher
	 */
	public KeyEventDispatcher getDispatcher() {
		return keyDispatcher;
	}
	
}
