package view;

import model.MyModel;
import presenter.MyPresenter;

public class Run {
	
	public static void main(String[] args) {
		presenter.Properties pr = new presenter.Properties();
		pr.setAlgorithmGenerateName("MyMaze3dGenerator");
		pr.setAlgorithmSearchName("BFS");
		pr.setNumOfThreads(10);
		pr.setXSize(15);
		pr.setYSize(1);
		pr.setZSize(15);
		pr.setUserInterface("GUI");
		pr.setMazeName("mainMaze");
		MyModel m = new MyModel(pr);
		MainWindow v=new MainWindow("Menu", 1000, 700);
		MyPresenter p = new MyPresenter(m,v);
		
		m.addObserver(p);
		v.addObserver(p);
		
		v.start();
	}
}
