package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;

/**
 * <h1>  class ServerView <h1>
 * This class 
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   28/1/16
 */
public class ServerView extends BasicWindow implements View{
	
	List list;
	
	/**
	 * C'tor
	 * @param String title
	 * @param int x
	 * @param int y
	 */
	public ServerView(String title, int x, int y) {
		super(title, x, y);
	}

	/**
	 * Initialize all the widgets in the window
	 */
	@Override
	void initWidgets() {
		
		shell.setLayout(new GridLayout(2, true));
		shell.setBackground(new Color(display, 100, 200, 225));
		shell.setImage(new Image(display, "resources/serverLogo.png"));
		
		Button startButton = new  Button(shell, SWT.PUSH);
		startButton.setText("Start");
		startButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		
		Button stopButton = new  Button(shell, SWT.PUSH);
		stopButton.setText("Stop");
		stopButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		
		Label l = new Label(shell, SWT.None);
		l.setText("Connected clients: ");
		l.setFont(new Font(display, "Calibri", 0, 7));
		l.setBackground(new Color(display, 100, 200, 225));
		
		Label status = new Label(shell,SWT.NONE);
		status.setText("Server is off                      ");
		status.setFont(new Font(display, "Calibri", 0, 7));
		status.setForeground(new Color(display, 255, 0, 0));
		status.setBackground(new Color(display, 100, 200, 225));
		list = new List(shell, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);    
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		startButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				notifyString("StartServer");
				status.setText("Server is on");
				status.setForeground(new Color(display, 255, 255, 0));
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		stopButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				status.setForeground(new Color(display, 0, 0, 255));
				status.setText("Server is turning off...");
				notifyString("StopServer");
				status.setText("Server is off");
				status.setForeground(new Color(display, 255, 0, 0));
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		list.addListener(SWT.MouseDoubleClick, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				String str = list.getItem(list.getFocusIndex());
				int port = Integer.parseInt(str.split(" ")[5]);
				String hostAddr = str.split(" ")[1];
				if(str.contains("disconnected") == false)
				{
					MessageBox messageBox = new MessageBox(shell,SWT.ICON_QUESTION| SWT.YES | SWT.NO);
					messageBox.setMessage("Do you really want to disconnect this client?");
					if(arg0.doit = messageBox.open () == SWT.YES)
						notifyString("disconnect "+port+" "+hostAddr);
				}
			}
		});
		
		shell.addListener(SWT.Close, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				notifyString("StopServer");
			}
		});
	}

	/**
	 * start the server
	 */
	public void start() {
		run();
	}
	
	/**
	 * notifyObservers(str)
	 * @param String str
	 */
	public void notifyString(String str)
	{
		setChanged();
		notifyObservers(str);
	}

	/**
	 * Open a messagebox
	 * @param String str
	 */
	public void displayString(String str)
	{
		MessageBox mb = new MessageBox(shell,SWT.OK);
		mb.setMessage(str);
		mb.open();
	}
	
	/**
	 * Display the client status in the list
	 * @param string str
	 */
	public void displayClient(String str)
	{
		String[] args = str.split(":");
		
		int port = Integer.parseInt(args[1].split(" ")[0]);
		String hostname = args[0];
		//System.out.println("str = "+str);
		
		display.asyncExec(new Runnable() {
			
			@Override
			public void run() {
				if(args[1].contains("has connected"))
				{
					list.add("client: " + hostname + " connected from port: " + port);
					list.redraw();
				}
				else
				{
					list.remove("client: " + hostname + " connected from port: " + port);
					list.add("client: " + hostname + " disconnected from port: " + port);
					list.redraw();
				}
			}
		});
	}
}
