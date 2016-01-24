package model;

import algorithms.mazeGenerators.Maze3d;

/**
 * <h1>  CommonModel class <h1>
 * This class define methods for each model system
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   17/12/15
 */
public abstract class CommonModel implements Model {

	/**
	 * Creates the maze
	 * @param int x,int y,int z(size),String type of generation,String maze name
	 */
	public abstract void generateMaze3d(int x, int y, int z, String generate, String name);

	/**
	 * Creates two-dimensional array that contain the section
	 * @param Maze3d maze.String maze name,int number section,char type section(X,Y,Z)
	 */
	public abstract void crossBySection(Maze3d maze, String name, int section, char typeSection);

	/**
	 * Save maze in file
	 * @param Maze3d maze name, String maze name, String file Name
	 */
	public abstract void saveMaze(Maze3d maze, String name, String fileName);

	/**
	 * Load maze from file
	 * @param String maze name, String file Name
	 */
	public abstract void loadMaze(String name, String fileName);

	/**
	 * Calculate size maze in the memory
	 * @param Maze3d maze name, String maze name
	 */
	public abstract void mazeSize(Maze3d maze, String name);

	/**
	 * Calculate size maze in the file
	 * @param String[] args
	 */
	public abstract void fileSize(String[] args);

	/**
	 * Creates solution to the maze
	 * @param String[] args, Maze3d maze
	 */
	public abstract void solveMaze(String[] args, Maze3d maze);

	/**
	 * Close all program
	 */
	public abstract void exit();

}
