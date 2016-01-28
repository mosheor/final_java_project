package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <h1>  class MyServer <h1>
 * This class is the server which doing all the calculations
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   24/01/16
 */
public class MyServer extends Observable{

	private int port;
	private ServerSocket server;
	private ClinetHandler clinetHandler;
	private int numOfClients;
	private ExecutorService threadpool;
	private volatile boolean stop;
	private Thread mainServerThread;
	private int clientsHandled=0;
	private ConcurrentHashMap<Integer, String> clients;
	private HashMap<Integer, Socket> clientSockets;
	
	/**
	 * Default C'tor
	 * @param port
	 * @param clinetHandler
	 * @param numOfClients
	 */
	public MyServer(int port,ClinetHandler clinetHandler,int numOfClients) {
		this.port=port;
		this.clinetHandler=clinetHandler;
		this.numOfClients=numOfClients;
		this.clients = new ConcurrentHashMap<Integer, String>();
		this.clientSockets = new HashMap<Integer,Socket>();
	}
	
	/**
	 * Start the server
	 * @throws Exception
	 */
	public void start() throws Exception{
		stop = false;
		server=new ServerSocket(port);
		server.setSoTimeout(10*1000);
		threadpool=Executors.newFixedThreadPool(numOfClients);
		
		mainServerThread=new Thread(new Runnable() {			
			@Override
			public void run() {
				while(!stop){
					try {
						final Socket someClient=server.accept();
						if(someClient!=null){
							threadpool.execute(new Runnable() {									
								@Override
								public void run() {
									try{
										clientsHandled++;
										System.out.println("\thandling client "+clientsHandled);
										notifyString(addClient(someClient));
										clientSockets.put(someClient.getPort(), someClient);
										clinetHandler.handleClient(someClient.getInputStream(), someClient.getOutputStream());
										clientSockets.remove(someClient.getPort());
										someClient.close();
										System.out.println("\tdone handling client "+clientsHandled);
										notifyString(removeClient(someClient));
										
									}catch(IOException e){
										e.printStackTrace();
									}									
								}
							});								
						}
					}
					catch (SocketTimeoutException e){
						System.out.println("no clinet connected...");
					} 
					catch (IOException e) {
						e.printStackTrace();
					}
				}
				System.out.println("done accepting new clients.");
			}
		});
		
		mainServerThread.start();
		
	}
	
	public void notifyString(String str)
	{
		System.out.println("MyServer notify = "+str);
		setChanged();
		notifyObservers(str);
	}
	
	public String addClient(Socket client){
		clients.put(client.getPort(), client.getLocalAddress().getHostAddress());
		System.out.println("add client: port = "+port+" , host = "+client.getLocalAddress().getHostAddress());
		System.out.println("client succed = "+clients.containsKey(client.getPort()));
		return ""+client.getLocalAddress().getHostAddress()+":"+client.getPort()+" has connected";
	}
	
	public String removeClient(Socket client){
		String hostAddr = null;
		if((hostAddr=clients.remove(client.getPort()))==null)
			return "Error with remove client";
		else
			return ""+hostAddr+":"+client.getPort()+" has disconnected";
	}
	
	/**
	 * Close the server
	 * @throws Exception
	 */
	public void close() throws Exception{
		if(stop==false)
		{
			stop=true;	
			// do not execute jobs in queue, continue to execute running threads
			System.out.println("shutting down");
			threadpool.shutdown();
			// wait 10 seconds over and over again until all running jobs have finished
			boolean allTasksCompleted=false;
			while(!(allTasksCompleted=threadpool.awaitTermination(10, TimeUnit.SECONDS)));
			System.out.println("all the tasks have finished");
			mainServerThread.join();	
			System.out.println("main server thread is done");
			server.close();
			System.out.println("server is safely closed");
			
		}
		else
		{
			server.close();
			System.out.println("server is safely closed");
		}
	}
	
	public void removeClientFromSockets(Integer port,String hostAddr)
	{
		Socket s =clientSockets.get(port);
		clientSockets.remove(port);
		System.out.println("port = "+port+","+s.getPort());
		System.out.println("addr = "+hostAddr+","+s.getInetAddress().getHostAddress().toString());
		try {
			/*PrintWriter p = new PrintWriter(s.getOutputStream());
			p.println("exit");
			p.flush();*/
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("clientSokets contain "+port+" = "+clientSockets.containsKey(port));
		notifyString(""+hostAddr+":"+port+" has disconnected");
	}
}

