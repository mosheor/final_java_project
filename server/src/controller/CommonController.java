package controller;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.State;
import model.Model;
import view.View;

public abstract class CommonController implements Controller{
	
	/**
	 * Set model
	 * @param Model model
	 */
	public abstract void setModel(Model model);
	/**
	 * Set view
	 * @param View view
	 */
	public abstract void setView(View view);
	/**
	 * Set maze3d to hashMap
	 * @param Maze3d maze,String maze name
	 */
	public abstract void setMaze3d(Maze3d maze,String name);
	/**
	 * Transfer to view two-dimensional array that contain the section
	 * @param Maze3d maze.String maze name,int number section,char type section(X,Y,Z)
	 */
	public abstract void crossSection(int[][] arr,char sectionType,String name,int section);
	/**
	 * Transfer to view string that he sould to present
	 * @param String str
	 */
	public abstract void printStr(String str);
	/**
	 * Load maze from hashmap
	 * @param Maze3d maze, String mazename
	 */
	public abstract void loadMaze(Maze3d maze, String name);
	/**
	 * Transfer to view arraylist that contain the solution to maze
	 * @param ArrayList<State<Position>> solution, String maze name
	 */
	public abstract void setSolution(ArrayList<State<Position>> solution, String name);

}
