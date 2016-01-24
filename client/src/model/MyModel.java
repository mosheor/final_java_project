package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import presenter.Properties;

/**
 * <h1>  MyModel class <h1>
 * This class is doing all the data calculations behind the scenes
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   17/01/16
 */
public class MyModel extends Observable implements Model,Observer
{
	
	private HashMap<String, Maze3d> mazeInFile;
	private HashMap<String, Maze3d> maze3dMap;
	private HashMap<String,Solution<Position>> solutionMap;
	private HashMap<Maze3d, Solution<Position>> mazeSolMap;
	private ExecutorService threadpool;
	private int[][] cross;
	private int index;
	private Properties properties;
	private GenerateMaze generateMazeOb;
	private SolveMaze solveMazeOb;

	
	/**
	 * Constructor - initialize controller
	 * @param HashMap<String, Command> commands map
	 */
	public MyModel(Properties properties) {
		this.mazeInFile = new HashMap<String, Maze3d>();
		this.maze3dMap = new HashMap<String, Maze3d>();
		this.solutionMap = new HashMap<String, Solution<Position>>();
		this.mazeSolMap = new HashMap<Maze3d, Solution<Position>>();
		this.properties = properties;
		this.cross = null;
		loadMaze3dMapZip();
		this.threadpool = Executors.newFixedThreadPool(properties.getNumOfThreads());
		
		generateMaze3d(properties.getXSize(),properties.getYSize(),
				properties.getZSize(),properties.getAlgorithmGenerateName(),properties.getMazeName());

		solveMaze(("solve "+properties.getMazeName()+" "+properties.getAlgorithmSearchName()).split(" ")
				, maze3dMap.get(properties.getMazeName()));
	}
	
	/**
	 * Comes here after notify event
	 * @param Observable o
	 * @param Object arg1
	 */
	@Override
	public void update(Observable o, Object arg1) {
		if(o==generateMazeOb)
		{
			if(arg1.getClass().getName().equals("java.lang.String"))
			{
				notifyString((String)arg1);
			}
		}
		if(o==solveMazeOb)
		{
			if(arg1.getClass().getName().equals("java.lang.String"))
			{
				notifyString((String)arg1);
			}
		}
	}
	
	/**
	 * Generates a maze and gives it's name
	 * @param int x
	 * @param int y
	 * @param int z
	 * @param String type of generation
	 * @param String maze name
	 */
	@Override
	public void generateMaze3d(int x, int y, int z, String generate,String name) {
		if(maze3dMap.containsKey(name)==true)
			notifyString("Maze "+name+" is alredy exists");
		else
		{
			generateMazeOb = new GenerateMaze();
			generateMazeOb.addObserver(this);
			Future<Maze3d> future = threadpool.submit(generateMazeOb.generate(x, y, z, generate, name));
			
			try {
				setMaze3d(future.get(),name);
			} catch (InterruptedException | ExecutionException e) {
				notifyString(e.getMessage());
			}
		}
	}
	
	
	public void setMaze3d(Maze3d maze,String name) {
		if(maze3dMap.containsKey(name)==false)
		{
			maze3dMap.put(name, maze);
			this.setChanged();
			notifyObservers();
		}
		else
		{
			maze3dMap.replace(name, maze);
			this.setChanged();
			notifyObservers();
		}
	}
	
	/**
	 * Creates two-dimensional array that contains the  requested section
	 * @param Maze3d maze
	 * @param String maze name
	 * @param int number section
	 * @param char type section(X,Y,Z)
	 */
	@Override
	public void crossBySection(Maze3d maze, String name, int section, char typeSection) {
		String s = null;
		if(typeSection=='x'){
			if(section<maze.getXSize() && section>=0)
			{
				setCross(maze.getCrossSectionByX(section));
				s = typeSection + " " + name + " " +section;
				index = 1;
				this.setChanged();
				notifyObservers(s);
			}
			else
				notifyString("error");
		}
		else if(typeSection=='y'){
			if(section<maze.getYSize() && section>=0)
			{
				cross = maze.getCrossSectionByY(section);
				s = typeSection + " " + name + " " +section;
				index = 1;
				this.setChanged();
				notifyObservers(s);
			}
			else
				notifyString("error");
		}
		else if(typeSection=='z'){
			if(section<maze.getZSize() && section>=0)
			{
				setCross(maze.getCrossSectionByZ(section));
				s = typeSection + " " + name + " " +section;
				index = 1;
				this.setChanged();
				notifyObservers(s);
			}
			else
				notifyString("error");
		}
	}
	
	/**
	 * Save maze in file
	 * @param Maze3d maze name
	 * @param String maze name
	 * @param String file Name
	 */
	@Override
	public void saveMaze(Maze3d maze, String name, String fileName) {
		MyCompressorOutputStream outFile;
		try {
			outFile = new MyCompressorOutputStream(new FileOutputStream(fileName));
			outFile.write(maze.toByteArray());
			System.out.println(fileName);
			mazeInFile.put(fileName, maze);
			outFile.close();
			notifyString("file "+fileName+" is ready");
		} catch (FileNotFoundException e) {
			notifyString(e.getMessage());
		} catch (IOException e) {
			notifyString(e.getMessage());
		}
	}

	/**
	 * Load maze from file
	 * @param String maze name
	 * @param String file Name
	 */
	@Override
	public void loadMaze(String name , String fileName) {
		MyDecompressorInputStream inFile;
		try {
			inFile = new MyDecompressorInputStream(new FileInputStream(fileName));
			byte[] b = new byte[4096];
			if(mazeInFile.containsKey(fileName)==true)
			{
				if(inFile.read(b)!=-1)
				{
					Maze3d maze = new Maze3d(b);
					if(maze3dMap.containsKey(name)==false)
					{
						maze3dMap.put(name, maze);
						notifyString("load maze "+name+" succeeded");
					}
					else
					{
						maze3dMap.replace(name, maze);
						notifyString("load maze "+name+" succeeded");
					}
				}
				else
				{
					index = -1;
					this.setChanged();
					notifyObservers("Can't load maze");
				}
			}
			else
			{
				notifyString("File " + fileName + " is not exist!");
			}
		} catch (FileNotFoundException e) {
			notifyString(e.getMessage());
		}
	}
	
	/**
	 * Calculates the maze size in the memory
	 * @param Maze3d maze name
	 * @param String maze name
	 */
	@Override
	public void mazeSize(Maze3d maze, String name) {
		notifyString("maze size " + name + " is " +(maze.getXSize()*maze.getYSize()*maze.getZSize()*Integer.SIZE)/8+ " bytes");
	}

	/**
	 * Calculate size maze in the file
	 * @param String[] args
	 */
	@Override
	public void fileSize(String[] args) {
		if(mazeInFile.containsKey(args[2])==true)
		{
			File f = new File(args[2]);
			notifyString("The size of the maze in the file is " +f.length()+ " bytes");
		}
		else
		{
			notifyString("File " + args[2] + " is not exist!");
		}
	}
	
	/**
	 * Creates solution to the maze
	 * @param String[] args
	 * @param Maze3d maze
	 */
	@Override
	public void solveMaze(String[] args, Maze3d maze) {
		if(mazeSolMap.containsKey(maze)==true)
			notifyString("Solution for maze "+args[1]+" is alredy exists");
		else
		{
			solveMazeOb = new SolveMaze();
			solveMazeOb.addObserver(this);
			Future<Solution<Position>> future = threadpool.submit(solveMazeOb.solve(args, maze));
			
			try {
				setMazeSol(future.get(), maze);
				setSolution(future.get(),args[1]);
			} catch (InterruptedException | ExecutionException e) {
				notifyString(e.getMessage());
			} 
		}
	}
	
	
	/**
	 * Stores the solution by maze name in the hash map of all the solutions
	 * @param Solution<Position> solution
	 * @param String maze name
	 */
	public void setSolution(Solution<Position> solution, String name) {
		if(solutionMap.containsKey(name)==false)
		{
			solutionMap.put(name, solution);
			this.setChanged();
			notifyObservers();
		}
		else
		{
			if(solutionMap.containsValue(solution)==false)
			{
				solutionMap.replace(name, solution);
				this.setChanged();
				notifyObservers();
			}
		}
	}
	
	/**
	 * Stores the solution by Maze3d class in the hash map of all the solutions
	 * @param Solution<Position> solution
	 * @param String Maze3d maze name
	 */
	public void setMazeSol(Solution<Position> solution, Maze3d maze) {
		if(mazeSolMap.containsKey(maze)==false)
		{
			mazeSolMap.put(maze, solution);
		}
		else
		{
			if(mazeSolMap.containsValue(solution)==false)
			{
				mazeSolMap.replace(maze, solution);
			}
		}
	}
	
	/**
	 * Close all program
	 */
	@Override
	public void exit() {
		//Zip save
		saveMaze3dMapZip();
		threadpool.shutdown();
		try {
			while(!(threadpool.awaitTermination(10, TimeUnit.SECONDS)));
		} catch (InterruptedException e) {
			this.setChanged();
			notifyString(e.getMessage());
		}
		notifyString("Exit");
	}
	
	/**
	 * Save the Maze3d hashmap to .zip file
	 */
	@Override
	public void saveMaze3dMapZip()
	{
		ObjectOutputStream obj = null;
		try {
			obj = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("mazeMap.zip")));
			obj.writeObject(maze3dMap);
			obj.flush();
		} catch (FileNotFoundException e) {
			notifyString(e.getMessage());
		} catch (IOException e) {
			notifyString(e.getMessage());
		} finally {
			try {
				obj.close();
			} catch (IOException e) {
				notifyString(e.getMessage());
			}
		}		
	}
	
	/**
	 * Load the Maze3d hashmap from .zip file
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void loadMaze3dMapZip()
	{
		ObjectInputStream obj = null;
		try {
			obj = new ObjectInputStream(new GZIPInputStream(new FileInputStream("mazeMap.zip")));
			maze3dMap = (HashMap<String, Maze3d>) obj.readObject();
		} catch (FileNotFoundException e) {
			notifyString(e.getMessage());
		} catch (IOException e) {
			notifyString(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if(obj!=null)
					obj.close();
			} catch (IOException e) {
				notifyString(e.getMessage());
			}
		}
	}

	/**
	 * Same as notifyObservers(str)
	 * @param String str
	 */
	@Override
	public void notifyString(String str) {
		index = 0;
		this.setChanged();
		notifyObservers(str);
	}
	
	/**
	 * Get all the existing mazes names
	 * @return String[]
	 */
	@Override
	public String[] getNamesMaze3d()
	{
		HashMap<String, Maze3d> temp = this.maze3dMap;
		String s=temp.toString();
		String[] t = s.split(", ");
		String[] k=null;
		for (int i = 0; i < t.length; i++) {
			if(i==0)
			{
				k = t[0].split("=");
				s = "";
				for (int j = 1; j < k[0].length(); j++) {
					s+=k[0].charAt(j);
				}
				t[0] = s;
			}
			else
			{
				
				k = t[i].split("=");
				t[i] = k[0];
			}
		}
		return t;
	}
	
	/**
	 * Hint for GUI
	 * @param String name
	 * @return
	 */
	//זו הפונקציה שמביאה את הרמז
	@Override
	public /*State<Position>*/ int getNumOfStepToGoal(String name)
	{
		//-> יחזיר את הפוזישן הראשון ופשוט נציג אותו
		if(maze3dMap.containsKey(name)==true)
		{
			solveMaze(("solve "+name+" "+properties.getAlgorithmSearchName()).split(" "),maze3dMap.get(name));
			
			return solutionMap.get(name).getSol()/*.get(0)*/.size();
		}
		else
			return -1;
	}
	
	/**
	 * Get the maze data by its name
	 * @param String name
	 * @return int[][][]
	 */
	@Override
	public int[][][] getArrayMaze3d(String name)
	{
		return maze3dMap.get(name).getMaze();
	}
	
	/**
	 * Get the maze class by its name
	 * @param String name
	 * @return Maze3d
	 */
	@Override
	public Maze3d getMaze3d(String name)
	{
		return maze3dMap.get(name);
	}
	
	/**
	 * Get the properties by class
	 * @return Properties
	 */
	@Override
	public Properties getProperties() {
		return properties;
	}
	
	/**
	 * Get the maze solution by its name
	 * @param String name
	 * @return Solution<Position>
	 */
	@Override
	public Solution<Position> getSolution(String name) {
		return solutionMap.get(name);
	}
	
	/**
	 * Check if the solution for a specific maze is already exist
	 * @param String name
	 * @return boolean
	 */
	@Override
	public boolean checkSolutionHash(String name)
	{
		return solutionMap.containsKey(name);
	
	}

	/**
	 * Get the current section
	 * @return int[][]
	 */
	@Override
	public int[][] getCross() {
		return cross;
	}
	
	/**
	 * Set the current section
	 * @param cross
	 */
	@Override
	public void setCross(int[][] cross) {
		this.cross = cross;
	}
	
	/**
	 * Get the index
	 * index = 1 - display the cross section
	 * else = 0 - display string
	 * @return int
	 */	
	@Override
	public int getIndex() {
		return index;
	}
	
	/**
 	 * index = 1 - display the cross section
	 * else = 0 - display string	 * @param int index
	 * @param int index
	 */	
	@Override
	public void setIndex(int index) {
		this.index = index;
	}
	
	/**
	 * Set the properties file
	 * @param Properties properties
	 */
	@Override
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	/**
	 * Check if the hashmap contains the maze by its name
	 * @param String name
	 * @return boolean
	 */
	@Override
	public boolean checkMazeHash(String name){
		return maze3dMap.containsKey(name);
	}

}



