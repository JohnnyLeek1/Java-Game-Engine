package dev.johnnyleek.engine.window;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.UUID;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import dev.johnnyleek.engine.input.EventType;
import dev.johnnyleek.engine.input.Keyboard;
import dev.johnnyleek.engine.input.Mouse;
import dev.johnnyleek.engine.render.Renderer;
import dev.johnnyleek.engine.sprite.Drawable;
import dev.johnnyleek.engine.util.Logger;

/**
 * This class serves as the generic Window which contains a DrawArea,
 * and can be rendered to.
 * 
 * Each window keeps track of it's own game and UI objects, as well
 * as Keyboard and Mouse input.
 * 
 * Keyboard and Mouse input can be disabled at instantiation using the
 * CreateWindow Window builder.
 * 
 * Each Window holds it's own Renderer which is used to render all
 * game and UI objects at runtime.
 * 
 * The order of objects added to the window is preserved, by utilizing
 * a LinkedHashMap.
 * 
 * 
 * @author Johnny Leek
 * @version 1.0
 *
 */
public class Window {
	
	private Dimension screenSize;
	private String title;
	private boolean visible = true;
	
	private JFrame window;
	private DrawArea drawArea;
	
	private int initDisplay = 0;
	private Renderer renderer;
	
	private LinkedHashMap<UUID, Drawable> gameObjects;
	private LinkedHashMap<UUID, Drawable> uiObjects;
	
	private KeyEventDispatcher keyboardDispatcher;
	
	private Mouse mouseDispatcher;
	
	/**
	 * Creates a new Window using the parameters passed from the CreateWindow Window builder
	 * @param windowBuilder the window builder object which contains information about this window
	 */
	private Window(CreateWindow windowBuilder) {
		Logger.info("Creating new window!");
		this.screenSize = new Dimension(windowBuilder.screenWidth, windowBuilder.screenHeight);
		
		this.title = windowBuilder.title;
		this.visible = windowBuilder.visible;
		this.initDisplay = windowBuilder.display;
		this.drawArea = new DrawArea();
		this.gameObjects = new LinkedHashMap<UUID, Drawable>();
		this.uiObjects = new LinkedHashMap<UUID, Drawable>();
		
		if(windowBuilder.allowKeyboard)
			allowKeyboard();		
		
		createWindow(windowBuilder.region);
		
		if(windowBuilder.allowMouse)
			allowMouse();
	}
	
	/**
	 * Helper method to instantiate the window and create the DrawArea
	 * @param region the region of the screen to open this window (default to MIDDLE_CENTER)
	 */
	private void createWindow(ScreenRegion region) {
		if(region == null) {
			region = ScreenRegion.MIDDLE_CENTER;
		}
		
		window = new JFrame(this.title);
		window.setSize(this.screenSize);
		window.setPreferredSize(this.screenSize);
		window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		setDisplay();
		
		this.drawArea.getArea().setSize(screenSize);
		this.drawArea.getArea().setPreferredSize(this.screenSize);
		
		window.add(drawArea.getArea());
		window.pack();
		window.setVisible(this.visible);
	}
	
	/**
	 * Adds a new KeyEventDispatcher to listen to keyboard input in this Window
	 */
	public void allowKeyboard() {
		if(keyboardDispatcher == null) {
			addKeyDispatcher(new Keyboard().getDispatcher());
		}
	}
	
	/**
	 * Adds the keyboard listener to the current window
	 * @param dispatcher the KeyEventDispatcher to add to the window
	 */
	private void addKeyDispatcher(KeyEventDispatcher dispatcher) {
		if(this.keyboardDispatcher == null) {
			this.keyboardDispatcher = dispatcher;
			KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(dispatcher);
		}
	}
	
	/**
	 * Allows the mouse listener to listen to this Window
	 */
	public void allowMouse() {
		if(mouseDispatcher == null) {
			addMouseListener(new Mouse());
		}
	}
	
	/**
	 * Adds appropriate mouse listeners to the Window
	 * @param mouse the mouse listeners to add into the window
	 */
	private void addMouseListener(Mouse mouse) {
		if(this.mouseDispatcher == null) {
			this.mouseDispatcher = mouse;
			
			this.drawArea.getArea().addMouseListener(mouseDispatcher);
			this.drawArea.getArea().addMouseMotionListener(mouseDispatcher);
		}
	}
	
	/**
	 * Sets the display (monitor/screen) to show the Window
	 */
	private void setDisplay() {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = env.getScreenDevices();
		
		try {
			this.window.setLocation(
					devices[this.initDisplay].getDefaultConfiguration().getBounds().x, 
					devices[this.initDisplay].getDefaultConfiguration().getBounds().y + window.getY()
					);
		} catch(IndexOutOfBoundsException e) {
			throw new RuntimeException(
					String.format("Selected display is not valid. Selected display is %d but there are only %d displays available", this.initDisplay, devices.length)
				);
		}
		
	}
	
	/**
	 * Gets the current Renderer for the Window (if one exists, else instantiate it)
	 * @return the Window's Renderer
	 */
	public Renderer getRenderer() {
		if(this.renderer == null) {
			this.renderer = new Renderer(this);
		}
		return this.renderer;
	}
	
	/**
	 * Gets the current DrawArea for the Window
	 * @return the Window's DrawArea
	 */
	public DrawArea getDrawArea() {
		return this.drawArea;
	}
	
	/**
	 * Gets the dimensions of the Window (width, height)
	 * @return Dimension containing the current screen size
	 */
	public Dimension getDimensions() {
		return this.screenSize;
	}
	
	/**
	 * Gets the JFrame that the Window is housed in
	 * @return the JFrame containing the Window
	 */
	public JFrame getFrame() {
		return this.window;
	}
	
	/**
	 * Gets all of the game elements (Drawable's) as a LinkedHashMap
	 * @return a LinkedHashMap containing all of the game elements contained in this Window
	 */
	public LinkedHashMap<UUID, Drawable> getGameElements() {
		return this.gameObjects;
	}
	
	/**
	 * Adds a new game element to the window
	 * @param sprite the game element to draw to the window
	 */
	public void addGameElement(Drawable sprite) {		
		this.gameObjects.put(sprite.getID(), sprite);
		Logger.debug("Added game sprite to Window: \"" + window.getTitle() + "\" with ID: " + sprite.getID());
	}
	
	/**
	 * Adds multiple new game elements to the window
	 * @param sprites the game elements to draw to the window
	 */
	public void addGameElements(Drawable ...sprites) {
		for(Drawable s : sprites) {
			addGameElement(s);
		}
	}
	
	/**
	 * Gets a game element by it's 'name' attribute. Returns null if game element matching the provided name
	 * is not found. Names are CaSe sensitive
	 * @param name the name of the element to grab
	 * @return the Drawable matching the name (or null if element not found)
	 */
	public Drawable getGameElementByName(String name) {
		for(Drawable sprite : gameObjects.values()) {
			if(sprite.getName() == name) return sprite;
		}
		
		return null;		
	}
	
	/**
	 * Gets all of the UI elements (Drawable's) as a LinkedHashMap
	 * @return a LinkedHashMap containing all of the UI elements contained in this Window
	 */
	public LinkedHashMap<UUID, Drawable> getUIElements() {
		return this.uiObjects;
	}

	/**
	 * Adds a new UI element to the window
	 * @param sprite the UI element to draw to the window
	 */
	public void addUIElement(Drawable sprite) {
		this.uiObjects.put(sprite.getID(), sprite);
		Logger.debug("Added UI Sprite to Window: \"" + window.getTitle() + "\" with ID: " + sprite.getID());
	}
	
	/**
	 * Adds multiple new UI elements to the window
	 * @param sprites the UI elements to draw to the window
	 */
	public void addUIElements(Drawable ...sprites) {
		for(Drawable s : sprites) {
			addUIElement(s);
		}
	}
	
	/**
	 * Gets a UI element by it's 'name' attribute. Returns null if UI element matching the provided name
	 * is not found. Names are CaSe sensitive
	 * @param name the name of the element to grab
	 * @return the Drawable matching the name (or null if element not found)
	 */
	public Drawable getUIElementByName(String name) {
		for(Drawable sprite : uiObjects.values()) {
			if(sprite.getName() == name) return sprite;
		}
		
		return null;		
	}
	
	/**
	 * Serves as a builder for a Window which allows for the
	 * dynamic creation of a window with specified parameters.
	 * 
	 * The window MUST be provided a width and a height.
	 * 
	 * Additional (optional) parameters include:
	 * 	- title: The title of the window
	 *  - isVisible: Whether or not the window is visible by default
	 *  - openAt: The region of the screen to open the window
	 *  - selectDisplay: The display to open the window on
	 *  - disableKeyboard: Disables keyboard input for this window
	 *  - disableMouse: Disables mouse input for this window
	 * 
	 * 
	 * @author Johnny Leek
	 * @version 1.0
	 *
	 */
	public static class CreateWindow {
		
		private int screenWidth, screenHeight;
		private String title;
		private boolean visible = true;
		private boolean allowKeyboard = true;
		private boolean allowMouse = true;
		private ScreenRegion region;
		private int display = 0;
		
		/**
		 * Instantiates the window builder. Must be provided with
		 * a width and height
		 * 
		 * @param screenWidth: The width (in pixels) of the window
		 * @param screenHeight: The height (in pixels) of the window
		 */
		public CreateWindow(int screenWidth, int screenHeight) {
			this.screenWidth = screenWidth;
			this.screenHeight = screenHeight;
		}
		
		/**
		 * Sets the title of the window.
		 * 
		 * @param title: The title of the window
		 * @return the modified builder object
		 */
		public CreateWindow title(String title) {
			this.title = title;
			return this;
		}
		
		/**
		 * Sets the visibility of the window.
		 * 
		 * @param visible: Whether or not the window is visible
		 * @return the modified builder object
		 */
		public CreateWindow isVisible(boolean visible) {
			this.visible = visible;
			return this;
		}
		
		/**
		 * Sets the region of the screen the window should open.
		 * @param region: The region of the screen as defined in ScreenRegion
		 * @return the modified builder object
		 */
		public CreateWindow openAt(ScreenRegion region) {
			this.region = region;
			return this;
		}
		
		/**
		 * Sets the monitor to display the window on.
		 * @param display: The display (as an integer) to display the frame on
		 * @return the modified builder object
		 */
		public CreateWindow selectDisplay(int display) {
			this.display = display;
			return this;
		}
		
		/**
		 * Disables keyboard input for the provided window.
		 * @return the modified builder object
		 */
		public CreateWindow disableKeyboard() {
			this.allowKeyboard = false;
			return this;
		}
		
		/**
		 * Disables mouse events for the provided window.
		 * @return the modified builder object
		 */
		public CreateWindow disableMouse() {
			this.allowMouse = false;
			return this;
		}
		
		/**
		 * Packs the settings into a window
		 * 
		 * @return the created window
		 */
		public Window pack() {
			Window window = new Window(this);
			return window;
		}
		
	}
}
