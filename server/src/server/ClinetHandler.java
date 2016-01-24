package server;

import java.io.InputStream;
import java.io.OutputStream;

import model.Model;

public interface ClinetHandler 
{
	//זמני
	void handleClient(InputStream inFromClient, OutputStream outToClient, Model model);
}
