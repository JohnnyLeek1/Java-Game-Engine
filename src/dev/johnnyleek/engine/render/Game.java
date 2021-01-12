package dev.johnnyleek.engine.render;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import dev.johnnyleek.engine.input.KeyBinding;
import dev.johnnyleek.engine.input.Keyboard;
import dev.johnnyleek.engine.util.Logger;
import dev.johnnyleek.engine.window.Window;

/**
 * Handles the general game logic, such as running the render loop,
 * and checking for the "runTick()" method, which is called every frame.
 * 
 * @author Johnny Leek
 * @version 1.0
 *
 */
public abstract class Game implements Runnable {

	private int fps = 60;
	private Thread gameThread;
	private boolean isRunning;
	
	private Window window;
	
	/**
	 * Creates the game loop, and runs the "stop" method on close
	 * @param window the window the game loop should run on
	 */
	public Game(Window window) {
		this.window = window;
		
		window.getFrame().addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Logger.info("Closing Window");
				stop();
				System.exit(0);
			}
		});
	}
	
	public abstract void runTick();
	
	
	/**
	 * Calculates the current time, and decides whether or not to run
	 * the next frame.
	 * 
	 * Running the next frame consists of calling the "runTick" method,
	 * and calling the renderer to render the next frame.
	 */
	@Override
	public void run() {
		double tickSpan = 1000000000 / fps;
		double deltaT = 0;
		long previous = System.nanoTime();
		
		while(this.isRunning) {
			long now = System.nanoTime();
			deltaT += (now - previous) / tickSpan;
			previous = now;
			if(deltaT >= 1) {
				runTick();
				window.getRenderer().render();
				deltaT--;
			}
		}
	}
	
	/**
	 * Starts the game thread
	 */
	public synchronized void start() {
		if(this.isRunning) return;
		Logger.info("Starting Game Loop");
		this.isRunning = true;
		this.gameThread = new Thread(this);
		this.gameThread.start();
	}
	
	/**
	 * Stops the game thread
	 */
	public synchronized void stop() {
		if(!this.isRunning) return;
		Logger.info("Stopping Game Loop");
		this.isRunning = false;
		try {
			this.gameThread.join();
		} catch(InterruptedException e) {
			Logger.error("Game thread was interrupted!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the window rendering on this game loop
	 * @return the game loops window
	 */
	public Window getWindow() {
		return this.window;
	}
	
}
