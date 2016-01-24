package model;

import java.util.Observable;
import java.util.concurrent.Callable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Astar;
import algorithms.search.BFS;
import algorithms.search.Maze3dDomain;
import algorithms.search.MazeAirDistance;
import algorithms.search.MazeManhattanDistance;
import algorithms.search.Searchable;
import algorithms.search.Solution;
import comperators.StateCostComparator;

/**
 * <h1>  class SolveMaze <h1>
 * This class is for the future<Sulution<<Position>>,
 * when the solution is ready its notifies the model
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   17/01/16
 */
public class SolveMaze extends Observable {
	
	public Callable<Solution<Position>>  solve(String[] args, Maze3d maze)
	{
		Callable<Solution<Position>> call = new Callable<Solution<Position>>() {
			@Override
			public Solution<Position> call() {
				Searchable<Position> s = new Maze3dDomain(maze);
				BFS<Position> solve = null;
				Solution<Position> sol = null;
				if(args.length>=3 && args.length<5)
				{
					if(args[2].equals("BFS")==true)
					{
						solve = new BFS<Position>(new StateCostComparator<Position>());
						sol = new Solution<Position>(solve.search(s).getSol());
						notifySol("solution for " +args[1]+ " is ready");
					}
					else
						notifySol("Algorithm is not exist");
				}
				else if(args.length>=5)
				{
					if((args[2]+" "+args[3]+" "+args[4]).equals("Astar Air Distance")==true)
					{
						solve = new Astar<Position>(new StateCostComparator<Position>(),new MazeAirDistance(s));
						sol = new Solution<Position>(solve.search(s).getSol());
						notifySol("solution for " +args[1]+ " is ready");
					}
					else if((args[2]+" "+args[3]+" "+args[4]).equals("Astar Manhattan Distance")==true)
					{
						solve = new Astar<Position>(new StateCostComparator<Position>(),new MazeManhattanDistance(s));
						sol = new Solution<Position>(solve.search(s).getSol());
						notifySol("solution for " +args[1]+ " is ready");
					}
					else
						notifySol("Algorithm is not exist");
				}
				else
					notifySol("Algorithm is not exist");
				return sol;
			}
		};
		return call;
	}

	private void notifySol(String str)
	{
		this.setChanged();
		notifyObservers(str);
	}

}
