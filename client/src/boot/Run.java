package boot;
/*
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import model.MyModel;
import presenter.MyPresenter;
import presenter.Properties;
import view.CLI;
import view.MyView;
*/
public class Run {
	/*	
	public static void main(String[] args) { 
		Properties pr = new Properties();
		pr.setAlgorithmGenerateName("MyMaze3dGenerator");
		pr.setAlgorithmSearchName("Astar Air Distance");
		pr.setNumOfThreads(10);
		pr.setXSize(12);
		pr.setYSize(5);
		pr.setZSize(12);
		pr.setUserInterface("GUI");
		pr.setMazeName("mainMaze");
		
		try {
			FileOutputStream file = new FileOutputStream("properties1.xml");
			BufferedOutputStream bos = new BufferedOutputStream(file);
			XMLEncoder s = new XMLEncoder(bos);
			s.writeObject(pr);
			s.flush();
			s.close();
			file.close();
			bos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Properties pr = new Properties();
		try {
			FileInputStream file = new FileInputStream("properties1.xml");
			BufferedInputStream bis = new BufferedInputStream(file);
			XMLDecoder s = new XMLDecoder(bis);
			pr = (Properties) s.readObject();
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		BufferedReader in = null;
		in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = null;
		out = new PrintWriter(new OutputStreamWriter(System.out));
	
		
		MyModel m = new MyModel(pr);
		CLI cli = new CLI(in,out);
		MyView v = new MyView(cli);
		cli.setView(v);
		MyPresenter p = new MyPresenter(m,v);
		
		// out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("ben.txt")));
			//	out.write("generate 3d maze ben 5 5 5 MyMaze3dGenerator\n");
			//	out.write("dir src\n");
			//	out.write("display ben\n");
			//	out.write("display cross section by Y 2 for ben\n");
			//	out.write("save maze ben fileMaze\n");
			//	out.write("load maze fileMaze ben\n");
			//	out.write("maze size ben\n");
			//	out.write("file size file\n");
			//	out.write("solve ben BFS\n");
			//	out.write("display solution ben\n");
			//	out.write("exit\n");
		 
		m.addObserver(p);
		v.addObserver(p);
		v.start();
		/*MyMaze3dGenerator m = new MyMaze3dGenerator(3,3,3);
		
		m.getMaze().printMaze();
		
		FileOutputStream om;
		GZIPOutputStream g;
		ObjectOutputStream o;
			try {
				om = new FileOutputStream("kkk.zip");
				g = new GZIPOutputStream(om);
				o = new ObjectOutputStream(g);
				o.write(m.getMaze().toByteArray());
				o.flush();
				o.close();
				System.out.println("nem");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		FileInputStream om1;
		GZIPInputStream g1;
		ObjectInputStream o1;
			try {
				om1 = new FileInputStream("kkk.zip");
				g1 = new GZIPInputStream(om1);
				o1 = new ObjectInputStream(g1);
				int c = 0;
				while((c=o1.read())!=-1)
					System.out.println(c);
				o1.close();
				System.out.println("nem");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} */
			
		/*HashMap<String, Maze3d> mh = new HashMap<String,Maze3d>();
		mh.put("ben", m.getMaze());
		FileOutputStream om;
		GZIPOutputStream g;
		ObjectOutputStream o;
			try {
				om = new FileOutputStream("kkk.zip");
				g = new GZIPOutputStream(om);
				o = new ObjectOutputStream(g);
				o.writeObject(mh);
				o.flush();
				o.close();
				System.out.println("nem");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}*/
}
