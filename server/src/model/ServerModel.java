package model;

import java.util.Observable;
import java.util.Observer;

import Presenter.Properties;

/**
 * <h1> class ServerModel <h1>
 * This class is the model of the server which doing all the calculations
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   28/01/16
 */
public class ServerModel extends Observable implements Observer,Model{
	
	private MyServer server;
	
	/**
	 * C'tor
	 * @param Propertirs properties
	 * @param ClientHandler cl
	 */
	public ServerModel(Properties properties,ClinetHandler cl) {
		if(properties.getClinetHandler().equals("Maze3dClientHandler")==true)
		{
			server = new MyServer(properties.getPort(), cl, properties.getNumOfClients());
			server.addObserver(this);
		}
	}

	/**
	 * Turn the server on
	 */
	public void StartServer()
	{
		try {
			server.start();
			notifyString("Server is connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Turn the server off
	 */
	public void CloseServer()
	{
		try {
			server.close();
			notifyString("Server is off");
		} catch (Exception e) {
			notifyString(e.getMessage());
		}
	}
	
	/**
	 * Disconnect from a client and stop his handling
	 * @param port
	 * @param String hostAddr
	 */
	public void disconnectClient(int port,String hostAddr)
	{
		server.removeClientFromSockets(port,hostAddr);
	}
	
	/**
	 * notifyObservers(str)
	 * @param String  str
	 */
	public void notifyString(String str)
	{
		setChanged();
		notifyObservers(str);
	}

	/**
	 * Override the update of Observer, After notify event
	 * @param Observable o
	 * @param Object arg
	 */
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
