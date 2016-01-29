package model;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * <h1> interface ClinetHandler <h1>
 * This class serves the client, defines the functionality of handling a client
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   28/01/16
 */
public interface ClinetHandler 
{
	/**
	 * Define how to handle the client
	 * @param InputStream inFromClient
	 * @param OutputStream outToClient
	 */
	void handleClient(InputStream inFromClient, OutputStream outToClient);
}
