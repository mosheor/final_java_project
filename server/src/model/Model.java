package model;

public interface Model {

	public void StartServer();
	
	public void CloseServer();
	
	public void disconnectClient(int port,String hostAddr);
	
	public void notifyString(String str);
	
}
