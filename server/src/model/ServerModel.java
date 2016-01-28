package model;

import java.util.Observable;
import java.util.Observer;

import Presenter.Properties;

public class ServerModel extends Observable implements Observer,Model{
	
	private MyServer server;
	
	public ServerModel(Properties properties,ClinetHandler cl) {
		if(properties.getClinetHandler().equals("Maze3dClientHandler")==true)
		{
			server = new MyServer(properties.getPort(), cl, properties.getNumOfClients());
			server.addObserver(this);
		}
	}

	public void StartServer()
	{
		try {
			server.start();
			notifyString("Server is connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void CloseServer()
	{
		try {
			server.close();
			notifyString("Server is off");
		} catch (Exception e) {
			notifyString(e.getMessage());
		}
	}
	
	public void disconnectClient(int port,String hostAddr)
	{
		server.removeClientFromSockets(port,hostAddr);
	}
	
	public void notifyString(String str)
	{
		setChanged();
		notifyObservers(str);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o==server)
		{
			if(arg!=null && arg.getClass().getName().equals("java.lang.String")==true)
			{
				notifyString((String)arg);
			}
		}
	}
}
