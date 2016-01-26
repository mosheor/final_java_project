package model;

import java.util.Observable;

import Presenter.Properties;

public class ServerModel extends Observable{
	
	private MyServer server;
	
	public ServerModel(Properties properties) {
		server = new MyServer(properties.getPort(), properties.getClinetHandler(), properties.getNumOfClients());
	}

	public void StartServer()
	{
		try {
			server.start();
			notifyString("Server is connected");
		} catch (Exception e) {
			notifyString(e.getMessage());
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
	
	public void notifyString(String str)
	{
		setChanged();
		notifyObservers(str);
	}
}
