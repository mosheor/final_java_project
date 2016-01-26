package model;

import java.util.Observable;
import java.util.concurrent.Callable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;

public class GenerateMaze extends Observable {
	
	public Callable<Maze3d>  generate(int x, int y, int z, String generate,String name)
	{
		Callable<Maze3d> call =  new Callable<Maze3d>() {
			@Override
			public Maze3d call() {
				Maze3dGenerator mg = null;
				if(generate.equals("MyMaze3dGenerator")==true)
					mg = new MyMaze3dGenerator(x,y,z);
				else
					mg = new SimpleMaze3dGenerator(x,y,z);
				notifyG("Maze "+name+" is ready");
				return mg.getMaze();
			}
		};
		return call;
	}

	private void notifyG(String str)
	{
		this.setChanged();
		notifyObservers(str);
	}
}
