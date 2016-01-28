package View;
import Presenter.MyPresenter;
import Presenter.Properties;
import model.Maze3dClientHandler;
import model.ServerModel;

public class RunServer {

	public static void main(String[] args) throws Exception{
		System.out.println("Server Side");
		
		Properties properties = new Properties();
		properties.setClinetHandler("Maze3dClientHandler");
		properties.setNumOfClients(10);
		properties.setPort(6000);
		
		if(properties.getClinetHandler().equals("Maze3dClientHandler")==true)
		{
			ServerModel model = new ServerModel(properties,new Maze3dClientHandler());
			ServerView view = new ServerView("server", 500, 500);
			MyPresenter presenter = new MyPresenter(model, view);
			view.addObserver(presenter);
			model.addObserver(presenter);
		
			view.start();
		}
	}	
}
