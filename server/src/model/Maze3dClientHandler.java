package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import algorithms.search.State;

/**
 * <h1> class Maze3dClientHandler <h1>
 * This class serves the client
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   24/01/16
 */
public class Maze3dClientHandler implements ClinetHandler,Observer{
	
	private GenerateMaze generateMazeOb;
	private SolveMaze solveMazeOb;
	private HashMap<String, Maze3d> maze3dMap;
	private HashMap<String,Solution<Position>> solutionMap;
	private HashMap<Maze3d, Solution<Position>> mazeSolMap;
	private ExecutorService threadpool;
	
	/**
	 * C'tor
	 */
	public Maze3dClientHandler() {
		this.maze3dMap = new HashMap<String, Maze3d>();
		this.solutionMap = new HashMap<String, Solution<Position>>();
		this.mazeSolMap = new HashMap<Maze3d, Solution<Position>>();
		threadpool = Executors.newFixedThreadPool(10);
		loadMaze3dMapZip();
	}
	
	/**
	 * serve the client
	 * @param InputStream inFromClient
	 * @param OutputStream outToClient
	 */
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {
		try{
			BufferedReader in=new BufferedReader(new InputStreamReader(inFromClient));
			PrintWriter out = new PrintWriter(outToClient);
			String line;
			Object o = null;
			while(!(line=in.readLine()).equals("exit")){
				String[] args = line.split(" ");
				
				switch(args[0]){
				
				case "generate":
					o = generateMaze3d(Integer.parseInt(args[4]), Integer.parseInt(args[5])
							,Integer.parseInt(args[6]), args[7], args[3]);
					out.println((String)o);
					out.flush();
					
					while((line  = in.readLine()).equals("getMaze")==false);
					
					byte[] byteArr = (getMaze3d(args[3])).toByteArray();
					for (byte b : byteArr) {
						out.write((int)b);
						out.flush();
					}
					break;
					
				case "display":
					if(args[1].equals("cross")==true)
					{
						int[][] cross = (int[][])crossBySection(maze3dMap.get(args[7]), args[7], Integer.parseInt(args[5]) ,
								args[4].toLowerCase().charAt(0));
						
						out.write(cross.length);
						out.flush();
						out.write(cross[0].length);
						out.flush();
						for (int i = 0; i < cross.length; i++) {
							for (int j = 0; j < cross[0].length; j++) {
								out.write(cross[i][j]);
								out.flush();
							}
						}
						break;
					}
					else if(args[1].equals("solution")==true)
					{
						System.out.println("display sol");
						Solution<Position> sol = getSolution(args[2]);
						ArrayList<State<Position>> arrSol = sol.getSol();
						System.out.println("arrSol is empty = "+arrSol.isEmpty());
						for (State<Position> state : arrSol) {
							System.out.println(state);
						}
						out.write(arrSol.size());
						out.flush();
						for (State<Position> b : arrSol) {
							System.out.println(b.toString());
							out.println(b.getState().toString());
							out.flush();
						}
						break;
					}
					else
					{
						o = getMaze3d(args[1]);
						byte[] byteArr1 = (getMaze3d(args[1])).toByteArray();
						for (byte b : byteArr1) {
							out.write((int)b);
							out.flush();
						}
						break;
					}
					
				case "solve":
					String solve = solveMaze(args, maze3dMap.get(args[1]));
					System.out.println("solve = "+solve);
					out.println(solve);
					out.flush();
					break;
				
				case "mazeName":
					String[] str =(String[])getNamesMaze3d();
					
					out.write(str.length);
					out.flush();
					for (String string : str) {
						out.println(string);
						out.flush();
					}
					break;
					
				case "hint":
					o = getNumOfStepToGoal(args[1], args[2]);
					out.write((int)o);
					out.flush();
					break;
				default:
					break;
				case "exit":
					exit();
					break;
				}
			}
			in.close();
			out.close();
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Ovveride the update of observer
	 */
	@Override
	public void update(Observable o, Object arg1) {
		if(o==generateMazeOb)
		{
			if(arg1.getClass().getName().equals("java.lang.String"))
			{
				System.out.println(arg1);
			}
		}
		if(o==solveMazeOb)
		{
			if(arg1.getClass().getName().equals("java.lang.String"))
			{
				System.out.println(arg1);
			}
		}
	}
	
	public Object generateMaze3d(int x, int y, int z, String generate,String name) {
		if(maze3dMap.containsKey(name)==true)
			return ("Maze "+name+" is alredy exists");
		else
		{
			generateMazeOb = new GenerateMaze();
			generateMazeOb.addObserver(this);
			Future<Maze3d> future = threadpool.submit(generateMazeOb.generate(x, y, z, generate, name));
			
			try {
				setMaze3d(future.get(),name);
				return ("Maze "+name+" is ready");
			} catch (InterruptedException | ExecutionException e) {
				System.out.println(e.getMessage());
			}
		}
		return null;
	}
	public void setMaze3d(Maze3d maze,String name) {
		if(maze3dMap.containsKey(name)==false)
		{
			maze3dMap.put(name, maze);
		}
		else
		{
			maze3dMap.replace(name, maze);
		}
	}
	public Object crossBySection(Maze3d maze, String name, int section, char typeSection) {
		if(typeSection=='x'){
			if(section<maze.getXSize() && section>=0)
				return maze.getCrossSectionByX(section);
			else
				System.out.println("Error");
		}
		else if(typeSection=='y'){
			if(section<maze.getYSize() && section>=0)
				return maze.getCrossSectionByY(section);
			else
				System.out.println("Error");
		}
		else if(typeSection=='z'){
			if(section<maze.getZSize() && section>=0)
				return maze.getCrossSectionByZ(section);
			else
				System.out.println("Error");
		}
		return null;
	}


	public Solution<Position> getSolution(String name) {
		System.out.println();
		return solutionMap.get(name);
	}
	public Maze3d getMaze3d(String name)
	{
		return maze3dMap.get(name);
	}
	public String solveMaze(String[] args, Maze3d maze) {
		if(mazeSolMap.containsKey(maze)==true)
			return ("Solution for maze "+args[1]+" is alredy exists");
		else
		{
			solveMazeOb = new SolveMaze();
			solveMazeOb.addObserver(this);
			Future<Solution<Position>> future = threadpool.submit(solveMazeOb.solve(args, maze));
			
			
			try {
				System.out.println("future is empty = "+future.get().getSol().isEmpty());
				setMazeSol(future.get(), maze);
				setSolution(future.get(),args[1]);
				return ("Solution for maze "+args[1]+" is ready");
			} catch (InterruptedException | ExecutionException e) {
				//System.out.println(e.getMessage());
				e.printStackTrace();
			} 
		}
		return null;
	}
	public void setSolution(Solution<Position> solution, String name) {
		if(solutionMap.containsKey(name)==false)
		{
			solutionMap.put(name, solution);
			System.out.println("contain sol = "+solutionMap.containsKey(name));
		}
		else
		{
			if(solutionMap.containsValue(solution)==false)
			{
				solutionMap.replace(name, solution);
				System.out.println("replace sol");
			}
		}
	}
	public void setMazeSol(Solution<Position> solution, Maze3d maze) {
		if(mazeSolMap.containsKey(maze)==false)
			mazeSolMap.put(maze, solution);
		else
		{
			if(mazeSolMap.containsValue(solution)==false)
				mazeSolMap.replace(maze, solution);
		}
	}

	public void saveMaze3dMapZip()
	{
		ObjectOutputStream obj = null;
		try {
			obj = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("mazeMap.zip")));
			obj.writeObject(maze3dMap);
			obj.flush();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				obj.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}		
	}
	@SuppressWarnings("unchecked")
	public void loadMaze3dMapZip()
	{
		ObjectInputStream obj = null;
		try {
			obj = new ObjectInputStream(new GZIPInputStream(new FileInputStream("mazeMap.zip")));
			maze3dMap = (HashMap<String, Maze3d>) obj.readObject();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if(obj!=null)
					obj.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public Object getNamesMaze3d()
	{
		HashMap<String, Maze3d> temp = this.maze3dMap;
		//System.out.println(maze3dMap.toString());
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
	public Object getNumOfStepToGoal(String name,String getAlgorithmSearchName)
	{
		if(maze3dMap.containsKey(name)==true)
		{
			solveMaze(("solve "+name+" "+getAlgorithmSearchName).split(" "),maze3dMap.get(name));
			return solutionMap.get(name).getSol().size();
		}
		else
			return -1;
	}
	public void exit() {
		//Zip save
		saveMaze3dMapZip();
		threadpool.shutdown();
		try {
			while(!(threadpool.awaitTermination(10, TimeUnit.SECONDS)));
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Exit client");
	}
}
