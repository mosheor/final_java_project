package view;	

import model.MyModel;
import presenter.MyPresenter;
import presenter.Properties;

public class Run {
	
	private static final int PORT = 6000;
	
	public static void main(String[] args) {
		Properties pr = new Properties();
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
		
		v.start();
	}
}
