package view;

import java.util.Observable;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * <h1>  class BasicWindow <h1>
 * This class present basic GUI window
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   17/01/16
 */
public abstract class BasicWindow extends Observable implements Runnable{

	Display display;
	Shell shell;
	
	/**
	 * C'tor
	 * @param String title
	 * @param int x
	 * @param int y
	 */
	public BasicWindow(String title,int x,int y) {
		display = new Display();
		shell = new Shell();
		shell.setText(title);
		shell.setSize(x,y);
	}
	
	/**
	 * Initialize all the widgets in the window
	 */
	abstract void initWidgets();
	
	/**
	 * Main events loop
	 */
	@Override
	public void run()
	{
		initWidgets();
		shell.open();
		
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}
		
		display.dispose();
	}
	
}
