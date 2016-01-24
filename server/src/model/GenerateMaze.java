package model;

import java.util.Observable;
import java.util.concurrent.Callable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;

/**
 * <h1>  class GenerateMaze <h1>
 * This class is for the future<.Maze3d.>,
 * when the maze is ready its notifies the model
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   17/01/16
 */
public class GenerateMaze extends Observable {
	
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
				
				notifyGenerated("Maze "+name+" is ready");
				return mg.getMaze();
			}
		};
		return call;
	}

	private void notifyGenerated(String str)
	{
		this.setChanged();
		notifyObservers(str);
	}
}
