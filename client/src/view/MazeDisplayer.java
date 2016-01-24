package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * <h1>  class MazeDisplayer <h1>
 * This class defines the Widget/Canvas MazeDisplayer
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   17/01/16
 */
public abstract class MazeDisplayer<T> extends Canvas{
	
	protected int[][] mazeData;
	protected MazeCharacter<Position> goalPosition;
	protected MazeCharacter<Position> character;
	protected volatile boolean finish;
	
	/**
	 * Get the maze array
	 * @return int[][]
	 */
	public int[][] getMazeData() {
		return mazeData;
	}
	/**
	 * C'tor (default)
	 * @param Composite parent
	 * @param int style
	 */
	public MazeDisplayer(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * Set the maze array
	 * @param int[][] mazeData
	 */
	public void setMazeData(int[][] mazeData){
		this.mazeData=mazeData;
	}
	
	/**
	 * Move the character up in the maze
	 * @param String str
	 */
	public abstract void moveUp(String str);

	/**
	 * Move the character down in the maze
	 * @param String str
	 */
	public abstract  void moveDown(String str);

	/**
	 * Move the character left in the maze
	 * @param String str
	 */
	public abstract  void moveLeft(String str);
	
	/**
	 * Move the character up right the maze
	 * @param String str
	 */
	public  abstract void moveRight(String str);

	/**
	 * Get the maze goal position
	 * @return goalPosition
	 */
	public abstract T getGoalPosition();
	
	/**
	 * Set the maze goal position
	 * @param goalPosition
	 */
	public abstract void setGoalPosition(T goalPosition);
	
	/**
	 * Get the maze character
	 * @return character
	 */
	public abstract T getCharacter();
	
	/**
	 * Set the maze character
	 * @param character
	 */
	public abstract void setCharacter(T character);
	
	/**
	 * Get all the possible moves in his current position
	 * @param Strung str
	 * @return String[]
	 */
	public abstract String[] possibleMoves(String str);
	
	/**
	 * Draw the maze with the character
	 * @param str
	 */
	public abstract void draw(String str);
	
	/**
	 * Get the solution of the maze
	 * @return Solution<T>
	 */
	public abstract Solution<T> getSol();

	/**
	 * Set the solution of the maze
	 * @return Solution<T>
	 */
	public abstract void setSol(Solution<T> sol);
	
	/**
	 * True - if the character reached the maze's goal position
	 * @return boolean
	 */
	public boolean isFinish() {
		return finish;
	}

	/**
	 * Set the character reached the maze's goal position
	 * @param boolean
	 */
	public void setFinish(boolean finish) {
		this.finish = finish;
	}

}