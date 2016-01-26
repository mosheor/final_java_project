package Presenter;

import java.io.Serializable;

import model.ClinetHandler;

/**
 * <h1>  Properties Interface <h1>
 * This class defines the game's properties
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   18/01/16
 */
public class Properties implements Serializable {

	//all the properties
	private static final long serialVersionUID = 1L;
	private int numOfClients;
	private int port;
	private ClinetHandler clinetHandler;
	
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

	public ClinetHandler getClinetHandler() {
		return clinetHandler;
	}

	public void setClinetHandler(ClinetHandler clinetHandler) {
		this.clinetHandler = clinetHandler;
	}

	//for canceling the error
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
