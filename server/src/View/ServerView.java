package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ServerView extends BasicWindow{
	
	public ServerView(String title, int x, int y) {
		super(title, x, y);
	}

	@Override
	void initWidgets() {
		
		shell.setLayout(new GridLayout(3, false));

		Button startButton = new  Button(shell, SWT.PUSH);
		startButton.setText("Start");
		
		startButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				notifyString("StartServer");
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
}
