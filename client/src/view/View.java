package view;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * <h1>  View Interface <h1>
 * This interface manage the view for the client
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   17/01/16
 */
public interface View
{
	/**
	 * start the view
	 */
	public void start();
	
	/**
	 * print any string 
	 * @param String s
	 */
	public void displayString(String s);
	
	/**
	 * Displays the maze data
	 * @param Maze3d maze
	 * @param String name
	 */
	public void displayMaze3d(Maze3d maze,String name);
	
	/**
	 * Display the maze solution
	 * @param Solution<Position> sol
	 * @param String name
	 */
	public void displaySolution(Solution<Position> sol,String name);
	
	/**
	 * NotifyObservers
	 * @param String[] args
	 */
	public void setCommand(String[] args);
	
	/**
	 * Set the args
	 * @param args
	 */
	public void setArgs(String[] args);
	
	/**
	 * Get the args
	 * @return args
	 */
	public String[] getArgs();
	
	/**
	 * Display the cross section (x/y/z)
	 * @param arr
	 * @param sectionType
	 * @param name
	 * @param section
	 */
	public void displayCrossSection(int[][] arr, String sectionType, String name, String section);
	
	/**
	 * Get all the mazes by name
	 * @return String[]
	 */
	public String[] getMazes();
	
	/**
	 * Get all the mazes by name
	 * @return mazes
	 */
	public void setMazes(String[] mazes);
	
	/**
	 * Set the solve algo
	 * @param solveAlg
	 */
	public void setSolveAlg(String solveAlg);
	
	/**
	 * Get the solve algo
	 * @return solveAlg
	 */
	public String getSolveAlg();
}