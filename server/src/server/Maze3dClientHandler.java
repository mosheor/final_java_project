package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import model.Model;
import model.MyModel;

public class Maze3dClientHandler implements ClinetHandler{

	Model model;
	
	
	public Maze3dClientHandler() {
		//model = new MyModel();
	}
	
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient, Model model) {
		try{
			BufferedReader in=new BufferedReader(new InputStreamReader(inFromClient));
			PrintWriter out=new PrintWriter(outToClient);
			String line;
			while(!(line=in.readLine()).equals("exit")){
				String[] args = line.split(" ");
				
				switch(args[0]){
				
				case "generate":
					Maze3d maze = model.generateMaze3d(Integer.parseInt(args[4]), Integer.parseInt(args[5]),
							Integer.parseInt(args[6]), args[7], args[3]);
					byte[] mazeByteArr = maze.toByteArray();
					
					String str = "Maze " + args[3] + " is ready";
					out.println(str);
					out.flush();

					while(!(line=in.readLine()).equals("getMaze"));
					for(byte b : mazeByteArr){
						out.write((int)b);
						out.flush();
					}
					break;
					
				case "solve":
					Solution<Position> solution = model.getSolution(args[1]);
					if(solution == null){
						out.println("Error to create solution");
						break;
					}
					out.println("solution for " + args[1] + " is ready");
					out.flush();
					
					while(!(line=in.readLine()).equals("getSolution"));
					ArrayList<State<Position>> solArr = solution.getSol();
					out.println(solArr.size());
					out.flush();
					for (State<Position> s : solArr) {
						out.println(s.toString());
						out.flush();
					}
					break;
				
				default:
					break;
				}
			}
			in.close();
			out.close();
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}

}
