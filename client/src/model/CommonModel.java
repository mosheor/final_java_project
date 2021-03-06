package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;

/**
 * <h1>  CommonModel class <h1>
 * This class define methods for each model system
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   17/12/15
 */
public abstract class CommonModel extends Observable implements Model {
	
	Socket serverSock;
	PrintWriter outToServer;
	BufferedReader inFromServer;
	
	public CommonModel(String ip,int port)
	{
		try {
			serverSock = new Socket(ip, port);
			if (serverSock.isConnected()==false) {
				exit();
			}
			else
			{
				outToServer = new PrintWriter(serverSock.getOutputStream());
				inFromServer = new BufferedReader(new InputStreamReader(serverSock.getInputStream()));
			}
		} catch (IOException e ) {
			notifyString(e.getMessage());
		}
	}
	
	/**
	 * Creates the maze
	 * @param int x,int y,int z(size),String type of generation,String maze name
	 */
	public abstract void generateMaze3d(int x, int y, int z, String generate, String name);

	/**
	 * Creates two-dimensional array that contain the section
	 * @param Maze3d maze.String maze name,int number section,char type section(X,Y,Z)
	 */
	public abstract void crossBySection(Maze3d maze, String name, int section, char typeSection);

	/**
	 * Save maze in file
	 * @param Maze3d maze name, String maze name, String file Name
	 */
	public abstract void saveMaze(Maze3d maze, String name, String fileName);

	/**
	 * Load maze from file
	 * @param String maze name, String file Name
	 */
	public abstract void loadMaze(String name, String fileName);

	/**
	 * Calculate size maze in the memory
	 * @param Maze3d maze name, String maze name
	 */
	public abstract void mazeSize(Maze3d maze, String name);

	/**
	 * Calculate size maze in the file
	 * @param String[] args
	 */
	public abstract void fileSize(String[] args);

	/**
	 * Creates solution to the maze
	 * @param String[] args, Maze3d maze
	 */
	public abstract void solveMaze(String[] args, Maze3d maze);

	/**
	 * Close all program
	 */
	public abstract void exit();
	
	public Socket getServerSock() {
		return serverSock;
	}

}
