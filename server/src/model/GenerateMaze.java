package model;

import java.util.Observable;
import java.util.concurrent.Callable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;

/**
 * <h1> class GenerateMaze <h1>
 * This class generates mazes for the server
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   24/01/16
 */
public class GenerateMaze extends Observable {
	
	/**
	 * The Generation function
	 * @param int x
	 * @param int y
	 * @param int z
	 * @param String generate
	 * @param String name
	 * @return Callable<.Maze3d.>
	 */
	public Callable<Maze3d> generate(int x, int y, int z, String generate,String name)
	{
		Callable<Maze3d> call =  new Callable<Maze3d>() {
			@Override
			public Maze3d call() {
				Maze3dGenerator mg = null;
				if(generate.equals("MyMaze3dGenerator")==true)
					mg = new MyMaze3dGenerator(x,y,z);
				else
					mg = new SimpleMaze3dGenerator(x,y,z);
				notifyString("Maze "+name+" is ready");
				return mg.getMaze();
			}
		};
		return call;
	}

	/**
	 * notifyObservers(str)
	 * @param String str
	 */
	private void notifyString(String str)
	{
		this.setChanged();
		notifyObservers(str);
	}
}
