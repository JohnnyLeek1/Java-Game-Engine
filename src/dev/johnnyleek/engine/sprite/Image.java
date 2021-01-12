package dev.johnnyleek.engine.sprite;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Represents an drawable image.
 * 
 * Images are created by loading a FileInputStream containing
 * the path to the image, and it's size is calculated dynamically.
 * 
 * Images can be instantiated either by using a FileInputStream, or by
 * providing a file path as a String.
 * 
 * Images can be changed using the "setImage" method, which will reload
 * the image and recalculate its size.
 * 
 * @author Johnny Leek
 * @version 1.0
 *
 */
public class Image extends Sprite {
	
	private BufferedImage image;
	
	/**
	 * Instantiates a new image using a FileInputStream
	 * @param imageStream a FileInputStream of the Image file
	 * @param posX the X position to render the Image
	 * @param posY the Y position to render the Image
	 * @throws IOException if the file cannot be read OR is not a valid Image
	 */
	public Image(FileInputStream imageStream, int posX, int posY) throws IOException {
		super(posX, posY);
		this.image = ImageIO.read(imageStream);
		super.setSize(new Dimension(this.image.getWidth(), this.image.getHeight()));
	}
	
	/**
	 * Instantiates a new image using a file path
	 * @param filePath a String containing the path to the Image file
	 * @param posX the X position to render the Image
	 * @param posY the Y position to render the Image
	 * @throws IOException if the file cannot be read OR is not a valid Image
	 */
	public Image(String filePath, int posX, int posY) throws IOException {
		super(posX, posY);
		this.image = ImageIO.read(new FileInputStream(filePath));
		super.setSize(new Dimension(this.image.getWidth(), this.image.getHeight()));
	}
	
	/**
	 * Gets the loaded BufferedImage
	 * @return the current Image
	 */
	public BufferedImage getImage() {
		return image;
	}
	
	/**
	 * Sets the new Image using a FileInputStream
	 * @param imageStream a FileInputStream of the Image file
	 * @throws IOException if the file cannot be read OR is not a valid image
	 */
	public void setImage(FileInputStream imageStream) throws IOException {
		this.image = ImageIO.read(imageStream);
		super.setSize(new Dimension(this.image.getWidth(), this.image.getHeight()));
	}
	
	/**
	 * Sets the new Image using a file path
	 * @param filePath a String containing the path to the Image file
	 * @throws IOException if the file cannot be read OR is not a valid image
	 */
	public void setImage(String filePath) throws IOException {
		this.image = ImageIO.read(new FileInputStream(filePath));
		super.setSize(new Dimension(this.image.getWidth(), this.image.getHeight()));
	}

}
