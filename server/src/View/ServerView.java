package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;

public class ServerView extends BasicWindow{
	
	List list;
	
	public ServerView(String title, int x, int y) {
		super(title, x, y);
	}

	@Override
	void initWidgets() {
		
		shell.setLayout(new GridLayout(2, true));

		Button startButton = new  Button(shell, SWT.PUSH);
		startButton.setText("Start");
		startButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		
		Button stopButton = new  Button(shell, SWT.PUSH);
		stopButton.setText("Stop");
		stopButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		
		new Label(shell, SWT.None).setText("Connected clients: ");
		
		list = new List(shell, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);    
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		startButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				notifyString("StartServer");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		stopButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				notifyString("StopServer");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		shell.addListener(SWT.Close, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				notifyString("StopServer");
			}
		});
	}
	
	private String[] getClient() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Runnable's start
	 */
	public void start() {
		run();
	}
	
	public void notifyString(String str)
	{
		setChanged();
		notifyObservers(str);
	}

	public void displayString(String str)
	{
		System.out.println(str);
	}
	
	public void displayClient(String str)
	{
		String[] args = str.split(":");
		
		int port = Integer.parseInt(args[1].split(" ")[0]);
		String hostname = args[0];
		System.out.println(str);
		
		display.asyncExec(new Runnable() {
			
			@Override
			public void run() {
				if(args[1].contains("has connected"))
				{
					list.add("client: " + hostname + " connected from port: " + port);
					System.out.println("add cli");
					list.redraw();
				}
				else
				{
					list.remove("client: " + hostname + " connected from port: " + port);
					System.out.println("rmv cli");
					list.add("client: " + hostname + " disconnected from port: " + port);
					list.redraw();
				}
			}
		});
	}
}
