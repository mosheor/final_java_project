package Presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import View.ServerView;
import model.ServerModel;

public class Presenter implements Observer{

	ServerModel model;
	ServerView view;
	HashMap<String, Command> commandsMap;
	
	public Presenter(ServerModel model,ServerView view) {
		this.view = view;
		this.model = model;
		this.commandsMap = new HashMap<String, Command>();
		putCommandsMap();
	}
	
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
				System.out.println("presenter update = "+(String)arg);
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
		
		commandsMap.put("GetConnectedClients", new Command() {
			
			@Override
			public void doCommand(String[] args) {
			}
		});
		
		commandsMap.put("GetNumberOfClients", new Command() {
			
			@Override
			public void doCommand(String[] args) {
			}
		});
	}
	
}
