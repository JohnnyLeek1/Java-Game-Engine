package dev.johnnyleek.engine.render;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

import dev.johnnyleek.engine.sprite.Drawable;
import dev.johnnyleek.engine.sprite.Image;
import dev.johnnyleek.engine.sprite.Shape;
import dev.johnnyleek.engine.sprite.Sprite;
import dev.johnnyleek.engine.sprite.Text;
import dev.johnnyleek.engine.util.Logger;
import dev.johnnyleek.engine.window.DrawArea;
import dev.johnnyleek.engine.window.Window;

/**
 * Handles rendering logic within a window.
 * 
 * @author Johnny Leek
 * @version 1.0
 *
 */
public class Renderer {

	private Window window;
	
	/**
	 * Provides the window to the renderer (to know which window to render on)
	 * @param window - the window to render on
	 */
	public Renderer(Window window) {
		this.window = window;
	}
	
	/**
	 * Renders the current frame.
	 * This method will return immediately if the DrawArea isDrawable field is false.
	 * This can be used to freeze the frame (the runTick() method will still be called every frame,
	 * so this can be used to pause the game).
	 * 
	 * If there is no buffer strategy on the DrawArea, create one with 3 buffers.
	 * 
	 * Renders the frame by performing the following logic:
	 * 	- Clears the frame
	 * 	- Iterate through each game object (element), and draw
	 * 	- Iterate through each UI object (UI element), and draw
	 * 
	 */
	public void render() {
		DrawArea area = window.getDrawArea();
		if(!area.isDrawable()) return;
		
		BufferStrategy bufferStrategy = area.getArea().getBufferStrategy();
		
		if(bufferStrategy == null) {
			area.getArea().createBufferStrategy(3);
			return;
		}
		
		Graphics graphics = bufferStrategy.getDrawGraphics();
		Graphics2D graphics2D = (Graphics2D) graphics;
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.clearRect(0, 0, window.getDimensions().width, window.getDimensions().height);
		
		for(Drawable sprite : window.getGameElements().values()) {
			if(sprite instanceof Shape) {
				Shape s = (Shape) sprite;
				graphics.setColor(s.getColor());
				switch(s.getShape()) {
					case RECTANGLE:
						graphics.fillRect(sprite.getPosX(), sprite.getPosY(), ((Sprite) sprite).getSize().width, ((Sprite) sprite).getSize().height);
						break;
					case CIRCLE:
						graphics.fillOval(sprite.getPosX(), sprite.getPosY(), ((Sprite) sprite).getSize().width, ((Sprite) sprite).getSize().height);
						break;
					default:
						break;
				}
				
			} else if(sprite instanceof Image) {
				graphics.drawImage(((Image) sprite).getImage(), sprite.getPosX(), sprite.getPosY(), null);
			} else if(sprite instanceof Text) {
				graphics2D.setColor(((Text) sprite).getColor());
				graphics2D.drawString(((Text) sprite).getText(), sprite.getPosX(), sprite.getPosY());
			}
		}
		
		for(Drawable sprite : window.getUIElements().values()) {
			if(sprite instanceof Shape) {
				Shape s = (Shape) sprite;
				graphics.setColor(s.getColor());
				switch(s.getShape()) {
					case RECTANGLE:
						graphics.fillRect(sprite.getPosX(), sprite.getPosY(), ((Sprite) sprite).getSize().width, ((Sprite) sprite).getSize().height);
						break;
					case CIRCLE:
						graphics.fillOval(sprite.getPosX(), sprite.getPosY(), ((Sprite) sprite).getSize().width, ((Sprite) sprite).getSize().height);
						break;
					default:
						break;
				}
				
			} else if(sprite instanceof Image) {
				graphics.drawImage(((Image) sprite).getImage(), sprite.getPosX(), sprite.getPosY(), null);
			} else if(sprite instanceof Text) {
				graphics2D.setColor(((Text) sprite).getColor());
				graphics2D.drawString(((Text) sprite).getText(), sprite.getPosX(), sprite.getPosY());
			}
		}
		
		bufferStrategy.show();
		graphics.dispose();
		
	}
	
}
