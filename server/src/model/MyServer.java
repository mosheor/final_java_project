package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
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
public class MyServer {

	int port;
	ServerSocket server;
	ClinetHandler clinetHandler;
	int numOfClients;
	ExecutorService threadpool;
	volatile boolean stop;
	Thread mainServerThread;
	int clientsHandled=0;
	
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
	}
	
	/**
	 * Start the server
	 * @throws Exception
	 */
	public void start() throws Exception{
		
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
										clinetHandler.handleClient(someClient.getInputStream(), someClient.getOutputStream());
										someClient.close();
										System.out.println("\tdone handling client "+clientsHandled);										
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
	
	/**
	 * Close the server
	 * @throws Exception
	 */
	public void close() throws Exception{
		stop=true;	
		// do not execute jobs in queue, continue to execute running threads
		System.out.println("shutting down");
		threadpool.shutdownNow();
		// wait 10 seconds over and over again until all running jobs have finished
		boolean allTasksCompleted=false;
		while(!(allTasksCompleted=threadpool.awaitTermination(10, TimeUnit.SECONDS)));
		System.out.println("all the tasks have finished");
		mainServerThread.join();	
		System.out.println("main server thread is done");
		server.close();
		System.out.println("server is safely closed");
	}
}

