package View;
import Presenter.Presenter;
import Presenter.Properties;
import model.Maze3dClientHandler;
import model.ServerModel;

public class RunServer {

	public static void main(String[] args) throws Exception{
		System.out.println("Server Side");
		
		Properties properties = new Properties();
		properties.setClinetHandler(new Maze3dClientHandler());
		properties.setNumOfClients(10);
		properties.setPort(6000);
		
		ServerModel model = new ServerModel(properties);
		ServerView view = new ServerView("server", 500, 500);
		Presenter presenter = new Presenter(model, view);
		view.addObserver(presenter);
		model.addObserver(presenter);
		
		view.start();
	}	
}
