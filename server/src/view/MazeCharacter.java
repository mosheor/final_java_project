package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;

/**
 * <h1>  class MazeCharacter <h1>
 * This class presents the character in the maze
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   17/01/16
 */
public class MazeCharacter<T> {
	
	private T character;
	private Image image;
	
	/**
	 * C'tor
	 * @param Image character
	 */
	public MazeCharacter(T character) {
		this.character = character;
		this.image = null;
	}
	
	/**
	 * C'tor
	 * @param Image character
	 */
	public MazeCharacter(Image image) {
		this.character = null;
		this.image = image;
	}
	
	/**
	 * C'tor
	 * @param T character
	 * @param Image image
	 */
	public MazeCharacter(T character,Image image) {
		this.character = character;
		this.image = image;
	}
	
	/**
	 * Draws the character
	 * @param PaintEvent e
	 * @param int srcX
	 * @param int srcY
	 * @param int srcWidth
	 * @param int srcHeight
	 * @param int destX
	 * @param int destY
	 * @param int destWidth
	 * @param int destHeight
	 */
	public void draw(PaintEvent e,int srcX,int srcY,int srcWidth,int srcHeight,int destX,int destY,int destWidth,int destHeight)
	{
		e.gc.drawImage(image, srcX, srcY, srcWidth, srcHeight, destX, destY, destWidth, destHeight);
	}

	/**
	 * Get the character position
	 * @return character position
	 */
	public T getCharacter() {
		return character;
	}

	/**
	 * Set the character position
	 * @param character position
	 */
	public void setCharacter(T character) {
		this.character = character;
	}

	/**
	 * Get the character's image
	 * @return Image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Set the character's image
	 * @param Image
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Check if the current character equals with his position in the maze
	 */
	public boolean equals(MazeCharacter<T> obj){
		return this.getCharacter().equals(obj.getCharacter());
	}

	/**
	 * Override the toString from Object
	 */
	@Override
	public String toString() {
		return this.character.toString();
	}
}
