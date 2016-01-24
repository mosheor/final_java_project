package view;
import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
/**
 * <h1>  MyView class <h1>
 * This class define methods for each view system
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   17/12/15
 */
public abstract class CommonView extends Observable implements View {

	String[] args;
	String[] mazes;
	String solveAlg;
	/**
	 * start the view
	 */
	public abstract void start();
	
	/**
	 * display any string 
	 * @param String s
	 */
	public abstract void displayString(String s);
	
	/**
	 * display maze3d
	 * @param Maze3d
	 * @param String - the maze
	 * @param maze name
	 */
	public abstract void displayMaze3d(Maze3d maze, String name);
	
	/**
	 * NotifyObservers
	 * @param String[] args
	 */
	public void setCommand(String[] args)
	{
		this.args = args;
		this.setChanged();
		notifyObservers();
	}
	
	
	/**
	 * Display the maze solution
	 * @param Solution<Position> sol
	 * @param String name
	 */
	public abstract void displaySolution(Solution<Position> sol,String name);
	
	
	/**
	 * Set the args
	 * @param args
	 */
	public abstract void setArgs(String[] args);
	
	/**
	 * Get the args
	 * @return args
	 */
	public abstract String[] getArgs();
	
	/**
	 * Display the cross section (x/y/z)
	 * @param arr
	 * @param sectionType
	 * @param name
	 * @param section
	 */
	public abstract void displayCrossSection(int[][] arr, String sectionType, String name, String section);
	
	/**
	 * Get all the mazes by name
	 * @return String[]
	 */
	public abstract String[] getMazes();
	
	/**
	 * Get all the mazes by name
	 * @return mazes
	 */
	public abstract void setMazes(String[] mazes);
	
	/**
	 * Set the solve algo
	 * @param solveAlg
	 */
	@Override
	public void setSolveAlg(String solveAlg) {
		this.solveAlg = solveAlg;		
	}
	
	/**
	 * Get the solve algo
	 * @return solveAlg
	 */
	@Override
	public String getSolveAlg() {
		return this.solveAlg;
	}
}
