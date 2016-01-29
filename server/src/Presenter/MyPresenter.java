package Presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import View.View;
import model.Model;

/**
 * <h1> class MyPresenter <h1>
 * This class manages all the program between the model and the view
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   18/1/16
 */
public class MyPresenter implements Observer{

	Model model;
	View view;
	HashMap<String, Command> commandsMap;
	
	/**
	 * C'tor
	 * @param Model model
	 * @param View view
	 */
	public MyPresenter(Model model,View view) {
		this.view = view;
		this.model = model;
		this.commandsMap = new HashMap<String, Command>();
		putCommandsMap();
	}
	
	/**
	 * Override the update of Observer, After notify event
	 * @param Observable o
	 * @param Object arg
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o==view)
		{
			if(arg!=null && arg.getClass().getName().equals("java.lang.String")==true)
			{
				String[] args = ((String)arg).split(" ");
				boolean dos = false;
				String s = null;
				for(int i=0;i<args.length;i++)
				{
					if(commandsMap.containsKey(s)== true && dos == false)
					{
						dos = true;
					}
					else
					{
						if(dos==false)
						{
							if(s==null)
								s = args[i];
							else if(s!=null)
								s = s + " " +args[i];
						}
					}
				}
				if(commandsMap.containsKey(s)==true)
				{
					commandsMap.get(s).doCommand(args);
				}
				else
				{
					view.displayString("Error");
				}
			}
		}
		else if(o==model)
		{
			if(arg!=null && arg.getClass().getName().equals("java.lang.String")==true)
			{
				//System.out.println("presenter update = "+(String)arg);
				if (((String)arg).contains("has")) {
					view.displayClient((String)arg);
				}
				else
				{
					view.displayString((String)arg);
				}
			}
		}
	}
	
	/**
	 * put all the presenter commands in the commands hashMap
	 */
	public void putCommandsMap()
	{
		commandsMap.put("StartServer", new Command() {
		
			@Override
			public void doCommand(String[] args) {
				model.StartServer();
			}
		});
		
		commandsMap.put("StopServer", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				model.CloseServer();
			}
		});
		
		commandsMap.put("GetNumberOfClients", new Command() {
			
			@Override
			public void doCommand(String[] args) {
			}
		});
		
		commandsMap.put("disconnect", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				model.disconnectClient(Integer.parseInt(args[1]),args[2]);
			}
		});
	}
	
}
