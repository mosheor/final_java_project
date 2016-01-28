package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
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
public class MyModel extends CommonModel implements Model
{
	
	private HashMap<String, Maze3d> mazeInFile;
	private HashMap<String, Maze3d> maze3dMap;
	private ExecutorService threadpool;
	private int[][] cross;
	private int index;
	private Properties properties;


	
	/**
	 * Constructor - initialize controller
	 * @param HashMap<String, Command> commands map
	 */
	public MyModel(String ip,Properties properties) {
		super(ip,properties.getServerPort());
		if (serverSock != null)
		{
			this.mazeInFile = new HashMap<String, Maze3d>();
			this.maze3dMap = new HashMap<String, Maze3d>();
			this.properties = properties;
			this.cross = null;
			this.threadpool = Executors.newFixedThreadPool(properties.getNumOfThreads());
			generateMaze3d(properties.getXSize(),properties.getYSize(),
					properties.getZSize(),properties.getAlgorithmGenerateName(),properties.getMazeName());
			loadMaze3dMapZip();
		}
		else
			notifyString("Exit because the server is null");
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
		{
			notifyString("Maze "+name+" is alredy exists");
		}
		else
		{
			
			outToServer.println("generate 3d maze "+name+" "+x+" "+y+" "+z+" "+generate);
			outToServer.flush();
			
			String line = null;
			String strReady = "Maze "+name+" is ready";
			String strAlready = "Maze "+name+" is alredy exists";
			try {
				while((line = (String)inFromServer.readLine()).equals(strReady)==false &&
						(line ).equals(strAlready)==false);
				notifyString(line);
			} catch (IOException e) {
				notifyString(e.getMessage());
			}
			
			outToServer.println("getMaze");
			outToServer.flush();
			byte[] byteArr = new byte[9+x*y*z];
			
			try {
				for (int i = 0; i < byteArr.length; i++) {
					byteArr[i] = (byte)inFromServer.read();
				}
			} catch (IOException e) {
				notifyString(e.getMessage());
			}
			
			Maze3d maze = new Maze3d(byteArr);
			setMaze3d(maze, name);
			
		}
	}
	
	
	/**
	 * Put the maze in the mazes HashMap
	 */
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
		outToServer.println("display cross section by "+typeSection+" "+section+" for "+name);
		outToServer.flush();
		
		try {
			cross = new int[inFromServer.read()][inFromServer.read()];
			for (int i = 0; i < cross.length; i++) {
				for (int j = 0; j < cross[0].length; j++) {
					cross[i][j] = inFromServer.read();
				}
			}
			String str = typeSection + " " + name + " " +section;
			index = 1;
			this.setChanged();
			notifyObservers(str);
		} catch (IOException e) {
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
		String str = "";
		for (String string : args) {
			str+=string+" ";
		}
		outToServer.println(str+"changeStartPos "+maze.getStartPosition().toString());
		outToServer.flush();
		
		String line = null;
		String strReady = "Solution for maze "+args[1]+" is ready";
		String strAlready = "Solution for maze "+args[1]+" is alredy exists";
		try {
			while((line = inFromServer.readLine()).equals(strReady)==false && 
					(line).equals(strAlready)==false);
			notifyString(strAlready);
		} catch (IOException e) {
			notifyString(e.getMessage());
		}
	}
	
	/**
	 * Close all program
	 */
	@Override
	public void exit() {
		outToServer.println("exit");
		outToServer.flush();
		threadpool.shutdown();
		try {
			while(!(threadpool.awaitTermination(10, TimeUnit.SECONDS)));
		} catch (InterruptedException e) {
			this.setChanged();
			notifyString(e.getMessage());
		}
		try {
			if (serverSock.isClosed()==false) {
				serverSock.close();
			notifyString("Exit");
			}
		} catch (IOException e) {
			notifyString(e.getMessage());		}
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
	@SuppressWarnings("deprecation")
	@Override
	public String[] getNamesMaze3d()
	{
		outToServer.println("mazeName");
		outToServer.flush();
		String[] str = null;
		String line;
		
		try {
			int size = inFromServer.read();
			System.out.println(size);
			str = new String[size];
			for (int i = 0; i < str.length; i++) {
				str[i] = (String)inFromServer.readLine();
			}
			return str;
		} catch (IOException  e) {
			notifyString(e.getMessage());
		}
		return null;
	}
	
	/**
	 * Hint for GUI
	 * @param String name
	 * @return
	 */
	@Override
	public int getNumOfStepToGoal(String name)
	{
		System.out.println("hint start pos = "+maze3dMap.get(name).getStartPosition().toString());
		outToServer.println("hint "+name+" "+properties.getAlgorithmSearchName()+
				" changeStartPos "+maze3dMap.get(name).getStartPosition().toString());
		outToServer.flush();
		int numOfSteps;
		
		try {
			numOfSteps = (int)inFromServer.read();
			return numOfSteps;
		} catch (IOException  e) {
			notifyString(e.getMessage());
		}
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
		outToServer.println("display solution "+name);
		outToServer.flush();
		
		try {
			int size = (int)inFromServer.read();
			ArrayList<State<Position>> sol = new ArrayList<State<Position>>();
			for (int i = 0; i < size; i++) {
				String s = inFromServer.readLine();
				String[] str = s.split(",");
				str[0] = str[0].substring(1, str[0].length());
				str[2] = str[2].substring(0, str[2].length()-1);
				Position p = new Position(Integer.parseInt(str[0]), Integer.parseInt(str[1]), Integer.parseInt(str[2]));
				sol.add(new State<Position>(p));
			}
			return new Solution<Position>(sol);
		} catch (IOException e) {
			notifyString(e.getMessage());
		}
		return null;
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

	/**
	 * Save maze in file
	 * @param Maze3d maze
	 * @param String name
	 * @param String fileName
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
	 * @param String name
	 * @param String fileName
	 */
	@Override
	public void loadMaze(String name, String fileName) {
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
	 * Load the maze HashMao from .zip file
	 */
	@Override
	public void loadMaze3dMapZip() {
		String[] mazesNames = getNamesMaze3d();
		for (String string : mazesNames) {
			outToServer.println("getMaze "+string);
			outToServer.flush();
			String line = null;
			try {
				while((line=inFromServer.readLine())==null);
			} catch (IOException e) {
				notifyString(e.getMessage());			}
			if(line.equals(string+" is not exist")==false)
			{
				String[] temp = line.split(",");
				byte[] byteArr = new byte[9+Integer.parseInt(temp[0])*Integer.parseInt(temp[1])*Integer.parseInt(temp[2])];
				
				try {
					for (int i = 0; i < byteArr.length; i++) {
						byteArr[i] = (byte)inFromServer.read();
					}
				} catch (IOException e) {
					notifyString(e.getMessage());
				}
				
				Maze3d maze = new Maze3d(byteArr);
				setMaze3d(maze, string);
			}
		}
		
	}
	
	

}



