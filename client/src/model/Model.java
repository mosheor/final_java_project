package model;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Properties;

/**
 * <h1>  Model Interface <h1>
 * This interface set the functional of the model side
 * This class is doing all the data calculations behind the scenes
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   17/01/16
 */
public interface Model {
		
	/**
	 * Creates the maze
	 * @param int x 
	 * @param int y
	 * @param int z
	 * @param String type of generation
	 * @param String maze name
	 */
	void generateMaze3d(int x,int y,int z,String generate,String name);
	
	/**
     * Creates two-dimensional array that contain the section
	 * @param Maze3d maze
	 * @param String maze name
	 * @param int section number
	 * @param char type section(X,Y,Z)
	 */
	void crossBySection(Maze3d maze,String name,int section,char typeSection);
	
	/**
	 * Saves a maze in file
	 * @param Maze3d maze name
	 * @param String maze name
	 * @param String file Name
	 */
	void saveMaze(Maze3d maze, String name, String fileName);
	
	/**
	 * Load maze from file
	 * @param String maze name
	 * @param String file Name
	 */
	void loadMaze(String name ,String fileName);
	
	/**
	 * Calculate size maze in the memory
	 * @param Maze3d maze name
	 * @param String maze name
	 */
	void mazeSize(Maze3d maze, String name);
	
	/**
	 * Calculate size maze in the file
	 * @param String[] args
	 */
	void fileSize(String[] args);
	
	/**
	 * Creates solution to the maze
	 * @param String[] args
	 * @param Maze3d maze
	 */		
	void solveMaze(String[] args, Maze3d maze);
	
	
	/**
	 * Exit and close the program
	 */
	void exit();
	
	/**
	 * Get the current section
	 * @return int[][]
	 */
	public int[][] getCross();
	
	/**
	 * Set the current section
	 * @param cross
	 */
	public void setCross(int[][] cross);
	
	/**
	 * Set the maze class by its name
	 * @param Maze3d maze
	 * @param String name
	 * @return int
	 */
	public int getIndex();
	
	/**
 	 * index = 1 - display the cross section
	 * else = 0 - display string	
	 * @param int index
	 */	
	public void setIndex(int index);
	
	/**
	 * Set the maze class by its name
	 * @param Maze3d maze
	 * @param String name
	 */
	public void setMaze3d(Maze3d maze,String name);
	
	/**
	 * Get the maze class by its name
	 * @param String name
	 * @return Maze3d
	 */
	public Maze3d getMaze3d(String name);
	
	/**
	 * Get the maze data by its name
	 * @param String name
	 * @return int[][][]
	 */
	public int[][][] getArrayMaze3d(String name);
	
	/**
	 * Check if the hashmap contains the maze by its name
	 * @param String name
	 * @return boolean
	 */
	public boolean checkMazeHash(String name);
	
	/**
	 * Same as notifyObservers(str)
	 * @param String str
	 */
	public void notifyString(String str);
	
	/**
	 * Get the maze solution by its name
	 * @param String name
	 * @return Solution<Position>
	 */
	public Solution<Position> getSolution(String name);

	/**
	 * Check if the solution for a specific maze is already exist
	 * @param String name
	 * @return boolean
	 */
	public boolean checkSolutionHash(String name);
	
	/**
	 * Save the Maze3d hashmap to .zip file
	 */
	public void saveMaze3dMapZip();
	
	/**
	 * Loads the Maze3d hashmap from .zip file
	 */
	public void loadMaze3dMapZip();
	
	/**
	 * Get all the existing mazes names
	 * @return String[]
	 */
	public String[] getNamesMaze3d();
	
	/**
	 * Hint for GUI
	 * @param String name
	 * @return
	 */
	public int getNumOfStepToGoal(String name);
	
	/**
	 * Set the properties file
	 * @param Properties properties
	 */
	public void setProperties(Properties properties);
	
	/**
	 * Get the properties by class
	 * @return Properties
	 */
	public Properties getProperties();	
}

