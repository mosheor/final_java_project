package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <h1>  CLI Class <h1>
 * This class manage the Command Line Interface for the client
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   17/01/16
 */
public class CLI implements Runnable{
	
	private BufferedReader in;
	private PrintWriter out;
	private View view;
	
	/**
	 * Constructor - initialize CLI
	 * @param in,out,hashMap BufferedReader ,PrintWriter,HashMap<String, Command>
	 */
	public CLI(BufferedReader in,PrintWriter out)
	{
		this.in = new BufferedReader(in);
		this.out = new PrintWriter(out);
	}
	
	/**
	 * Type of Runnable so we override run function
	 */
	@Override
	public void run() {
		this.start();
	}

	/**
	 * Start the thread manage all commands
	 */
	public void start()
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				String[] args = null;
				String s = null;
				try {
					while((s=in.readLine()).equals("exit")!=true)
					{
						args = s.split(" ");
						view.setCommand(args);
					}
				} catch (IOException e) {
					view.displayString(e.getMessage());
				}
				view.setCommand(args = s.split(" "));
			}
		},"cli thread").start();
	}
	/**
	 * Get in
	 * @return BufferedReader
	 */
	public BufferedReader getIn() {
		return in;
	}
	/**
	 * Set in
	 * @param BufferedReader
	 */
	public void setIn(BufferedReader in) {
		this.in = in;
	}
	/**
	 * Get out
	 * @return PrintWriter
	 */
	public PrintWriter getOut() {
		return out;
	}
	/**
	 * Set out
	 * @param PrintWriter
	 */
	public void setOut(PrintWriter out) {
		this.out = out;
	}
	
	/**
	 * Get view
	 * @return View
	 */	
	public View getView() {
		return view;
	}

	/**
	 * Set view
	 * @param View
	 */		
	public void setView(View view) {
		this.view = view;
	}
}
