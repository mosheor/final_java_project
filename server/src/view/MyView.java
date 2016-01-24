package view;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;

/**
 * <h1>  MyView class <h1>
 * This class implements View and manages the view for the client
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   17/01/16
 */
public class MyView extends CommonView{

	private CLI cli;
	/**
	 * Constructor - initialize view
	 * @param Controller,CLI
	 */
	public MyView(CLI cli) {
		this.cli = cli;
		this.solveAlg = null;
	}
	
	/**
	 * start the view
	 */
	@Override
	public void start() {
		cli.start();
	}

	/**
	 * print any string 
	 * @param String s
	 */
	@Override
	public void displayString(String s) {
		cli.getOut().println(s);
		cli.getOut().flush();
	}
	
	/**
	 * print maze3d
	 * @param Maze3d
	 * @param String - the maze
	 * @param maze name
	 */
	@Override
	public void displayMaze3d(Maze3d maze,String name) {
		int[][][] arr = maze.getMaze();
		cli.getOut().println("Maze name: "+name);
		for(int i=0;i<arr.length;i++)
		{
			for(int j=0;j<arr[0].length;j++)
			{
				for(int k=0;k<arr[0][0].length;k++)
				{
					cli.getOut().print(arr[i][j][k] + " ");
				}
				cli.getOut().println();
			}
			cli.getOut().println();
		}
		cli.getOut().flush();
	}

	/**
	 * Display the cross section (x/y/z)
	 * @param arr
	 * @param sectionType
	 * @param name
	 * @param section
	 */
	@Override
	public void displayCrossSection(int[][] arr, String sectionType, String name,String section) {
		cli.getOut().println("Maze name: "+name);
		cli.getOut().println("Section by "+sectionType+" = "+section);
		for(int i=0;i<arr.length;i++)
		{
			for(int j=0;j<arr[0].length;j++)
			{
				cli.getOut().print(arr[i][j] + " ");
			}
			cli.getOut().println();
		}
		cli.getOut().flush();
	}

	/**
	 * Display the maze solution
	 * @param Solution<Position> sol
	 * @param String name
	 */
	@Override
	public void displaySolution(Solution<Position> sol,String name) {
		if(sol!=null)
		{
			ArrayList<State<Position>> print = sol.getSol();
			displayString("Solution of maze "+name+" is:");
			for (State<Position> state : print) {
				cli.getOut().println(state.toString());
			}
			cli.getOut().flush();
		}
		else
			this.displayString("Solution is not exist");
	}
	
	/**
	 * Get the args
	 * @return args
	 */
	public String[] getArgs() {
		return args;
	}

	/**
	 * Set the args
	 * @param args
	 */
	public void setArgs(String[] args) {
		this.args = args;
	}

	/**
	 * Get all the mazes by name
	 * @return String[]
	 */
	@Override
	public String[] getMazes() {
		return this.mazes;
	}

	/**
	 * Set all the mazes by name
	 * @param String[]
	 */
	@Override
	public void setMazes(String[] mazes) {
		this.mazes = mazes;
		for (String string : mazes) {
			System.out.println(string);
		}
	}
}



