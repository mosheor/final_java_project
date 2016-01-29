package model;

/**
 * <h1> interface Model <h1>
 * This interface presents a functionality of the server model
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   28/01/16
 */
public interface Model {

	/**
	 * Start the server
	 */
	public void StartServer();
	
	/**
	 * Stop the server
	 */
	public void CloseServer();
	
	/**
	 * Disconnect specific client
	 * @param int port
	 * @param String hostAddr
	 */
	public void disconnectClient(int port,String hostAddr);
	
	/**
	 * notifyObservers(str)
	 * @param String str
	 */
	public void notifyString(String str);
	
}
