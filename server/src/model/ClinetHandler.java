package model;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public interface ClinetHandler 
{
	void handleClient(InputStream inFromClient, OutputStream outToClient);
}
