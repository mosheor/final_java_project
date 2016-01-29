package boot;	

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import model.MyModel;
import presenter.MyPresenter;
import presenter.Properties;
import view.MainWindow;

public class RunClient {
	
	
	public static void main(String[] args) {
		Properties pr = new Properties();
		/*pr.setAlgorithmGenerateName("MyMaze3dGenerator");
		pr.setAlgorithmSearchName("BFS");
		pr.setNumOfThreads(10);
		pr.setXSize(15);
		pr.setYSize(15);
		pr.setZSize(15);
		pr.setUserInterface("GUI");
		pr.setMazeName("mainMaze");
		pr.setServerPort(6000);*/
		
		try {
			FileInputStream file = new FileInputStream("properties.xml");
			BufferedInputStream bis = new BufferedInputStream(file);
			XMLDecoder s = new XMLDecoder(bis);
			pr = (Properties) s.readObject();
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		MyModel m = new MyModel("localhost",pr);
		MainWindow v=new MainWindow("Menu", 1000, 700);
		MyPresenter p = new MyPresenter(m,v);
		
		m.addObserver(p);
		v.addObserver(p);
		
		v.start();
	}
}
