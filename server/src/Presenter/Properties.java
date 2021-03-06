package Presenter;

import java.io.Serializable;

/**
 * <h1>  Properties Interface <h1>
 * This class defines the game's properties
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   18/01/16
 */
@SuppressWarnings("serial")
public class Properties implements Serializable {

	//all the properties
	//private static final long serialVersionUID = 1L;
	private int numOfClients;
	private int port;
	private String clinetHandler;
	
	/**
	 * Default C'tor
	 */
	public Properties() {
		this.clinetHandler = null;
		this.numOfClients = 0;
		this.port = 2000;
	}

	public int getNumOfClients() {
		return numOfClients;
	}

	public void setNumOfClients(int numOfClients) {
		this.numOfClients = numOfClients;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getClinetHandler() {
		return clinetHandler;
	}

	public void setClinetHandler(String clinetHandler) {
		this.clinetHandler = clinetHandler;
	}
	
}
