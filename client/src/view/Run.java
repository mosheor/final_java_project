package view;

import java.util.ArrayList;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import model.MyModel;
import presenter.MyPresenter;

public class Run {
	
	private static final int PORT = 6000;
	
	public static void main(String[] args) {
		/*presenter.Properties pr = new presenter.Properties();
		pr.setAlgorithmGenerateName("MyMaze3dGenerator");
		pr.setAlgorithmSearchName("BFS");
		pr.setNumOfThreads(10);
		pr.setXSize(15);
		pr.setYSize(1);
		pr.setZSize(15);
		pr.setUserInterface("GUI");
		pr.setMazeName("mainMaze");
		MyModel m = new MyModel("localhost",PORT,pr);
		MainWindow v=new MainWindow("Menu", 1000, 700);
		MyPresenter p = new MyPresenter(m,v);
		
		m.addObserver(p);
		v.addObserver(p);
		
		v.start();*/
		
		Position p = new Position(100,255,336);
		String line = p.toString();
		String[] temp = line.split(",");
		
		System.out.println(temp[0]);
		System.out.println(temp[2]);
		
		temp[0] = temp[0].substring(1, temp[0].length());
		temp[2] = temp[2].substring(0, temp[2].length()-1);
		
		System.out.println(temp[0]);
		System.out.println(temp[2]);
	}
}
