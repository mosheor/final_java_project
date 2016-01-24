package controller;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.State;
import model.Model;
import view.View;

/**
 * <h1>  Controller Interface <h1>
 * This interface manage all the program between the model and view
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   17/12/15
 */
public interface Controller {

	/**
	 * Set model
	 * @param Model model
	 */
	void setModel(Model model);
	/**
	 * Set view
	 * @param View view
	 */
	void setView(View view);
	/**
	 * Set maze3d to hashMap
	 * @param Maze3d maze,String maze name
	 */
	void setMaze3d(Maze3d maze,String name);
	/**
	 * Transfer to view two-dimensional array that contain the section
	 * @param Maze3d maze.String maze name,int number section,char type section(X,Y,Z)
	 */
	void crossSection(int[][] arr,char sectionType,String name,int section);
	/**
	 * Transfer to view string that he sould to present
	 * @param String str
	 */
	void printStr(String str);
	/**
	 * Load maze from hashmap
	 * @param Maze3d maze, String mazename
	 */
	void loadMaze(Maze3d maze, String name);
	/**
	 * Transfer to view arraylist that contain the solution to maze
	 * @param ArrayList<State<Position>> solution, String maze name
	 */
	void setSolution(ArrayList<State<Position>> solution, String name);
	
}
