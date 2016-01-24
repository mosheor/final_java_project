package view;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import model.MyModel;
import presenter.MyPresenter;
import presenter.Properties;


/**
 * <h1>  class MainWindow <h1>
 * This class present the main project GUI window
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   18/01/16
 */
public class MainWindow extends BasicWindow implements View{

	Timer timer;
	TimerTask task;
	private String[] args;
	private String[] mazes;
	private String nameCurrentMaze;
	private MazeDisplayer<Position> mazeDisplayer;
	private Maze3d maze;
	private Group arrowsGroup;
	private String solveAlg;
	private String section;
	
	/**
	 * C'tor
	 * @param String title
	 * @param int x
	 * @param int y
	 */
	public MainWindow(String title, int x, int y) {
		super(title, x, y);
	}
	
	/**
	 * Initialize all the widgets in the window
	 */
	@Override
	void initWidgets() {
		
		shell.setLayout(new GridLayout(2,false));
		
		Menu menuBar, fileInMenuBar, gameInMenuBar, helpInMenuBar;
		MenuItem fileMenuHeader, gameMenuHeader, helpMenuHeader, 
		openPropertiesItem, exitItem, aboutItem, saveMazeInFileItem, loadMazeInFileItem, mazeSizeItem, fileSizeItem;
		
		menuBar = new Menu(shell,SWT.BAR);
		
		fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&File");
		
		fileInMenuBar = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileInMenuBar);
		
		openPropertiesItem = new MenuItem(fileInMenuBar, SWT.PUSH);
		openPropertiesItem.setText("&Open Properties");
		
		exitItem = new MenuItem(fileInMenuBar, SWT.PUSH);
		exitItem.setText("&Exit");
		
		gameMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		gameMenuHeader.setText("&Game");
		
		gameInMenuBar = new Menu(shell, SWT.DROP_DOWN);
		gameMenuHeader.setMenu(gameInMenuBar);

		saveMazeInFileItem = new MenuItem(gameInMenuBar, SWT.PUSH);
		saveMazeInFileItem.setText("&Save maze in file");
		
		loadMazeInFileItem = new MenuItem(gameInMenuBar, SWT.PUSH);
		loadMazeInFileItem.setText("&Load maze from file");
		
		mazeSizeItem = new MenuItem(gameInMenuBar, SWT.PUSH);
		mazeSizeItem.setText("&Maze size");
		
		fileSizeItem = new MenuItem(gameInMenuBar, SWT.PUSH);
		fileSizeItem.setText("&File size");
		
		helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		helpMenuHeader.setText("&Help");
		
		helpInMenuBar = new Menu(shell, SWT.DROP_DOWN);
		helpMenuHeader.setMenu(helpInMenuBar);
		
		aboutItem = new MenuItem(helpInMenuBar, SWT.PUSH);
		aboutItem.setText("&About");
		
		shell.setMenuBar(menuBar);
		
		//////////////////////////////widgets//////////////////////////////////
		//Buttons group
		Group buttonsGroup = new Group(shell, SWT.NONE);
		buttonsGroup.setText("Options:");
		buttonsGroup.setLayout(new GridLayout(1, true));
		
		//generate maze button
		Button generateButton=new Button(buttonsGroup, SWT.PUSH);
		generateButton.setText("Generate maze3d");
		generateButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
				
		//dispaly maze button
		Button displayMazeButton=new Button(buttonsGroup, SWT.PUSH);
		displayMazeButton.setText("Display maze");
		displayMazeButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		
		//MazeDisplayer
		//mazeDisplayer = new Maze2D(shell, SWT.BORDER);
		mazeDisplayer = new Maze2D<Position>(shell, SWT.BORDER,new Image(display, "resources/goalPos.jpg"),
				new Image(display, "resources/piratesIm.jpg"),new Image(display, "resources/coin.jpg"),new Image(display, "resources/Treasure.jpg"));
		mazeDisplayer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4));
		mazeDisplayer.draw(null);
		
		//display solution maze button
		Button solveButton=new Button(buttonsGroup, SWT.PUSH);
		solveButton.setText("Display solution");
		solveButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		
		//move character to goal button
		Button solButton=new Button(buttonsGroup, SWT.PUSH);
		solButton.setText("Solve maze");
		solButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
						
		//hint button
		Button hintButton=new Button(buttonsGroup, SWT.PUSH);
		hintButton.setText("Hint");
		hintButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		
		//Sections group
		Group sectionGroup = new Group(shell, SWT.SHADOW_OUT);
		sectionGroup.setText("Choose section:");
		sectionGroup.setLayout(new GridLayout(1, true));
		
		//Sections radio buttons
		Button sectionXButton=new Button(sectionGroup, SWT.RADIO | SWT.SELECTED);
		sectionXButton.setText("Section by X");
		sectionXButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		Button sectionYButton=new Button(sectionGroup, SWT.RADIO | SWT.SELECTED);
		sectionYButton.setText("Section by Y");
		sectionYButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		Button sectionZButton=new Button(sectionGroup, SWT.RADIO | SWT.SELECTED);
		sectionZButton.setText("Section by Z");
		sectionZButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		
		//arrows group
		arrowsGroup = new Group(shell, SWT.NONE);
		arrowsGroup.setText("Posible moves:");
		arrowsGroup.setLayout(new GridLayout(3, false));
		
		Button[] b = new Button[6];
		
		b[0] = new Button(arrowsGroup, SWT.NONE);
		b[0].setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 2, 1));
		b[1] = new Button(arrowsGroup, SWT.NONE);
		b[1].setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		b[2] = new Button(arrowsGroup, SWT.NONE);
		b[2].setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		b[3] = new Button(arrowsGroup, SWT.NONE);
		b[3].setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		b[4] = new Button(arrowsGroup, SWT.NONE);
		b[4].setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 2, 1));
		b[5] = new Button(arrowsGroup, SWT.NONE);
		b[5].setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		
		possibleMoves(b);
		
		/////////////////////////////////////listeners///////////////////////////////////
		
		fileSizeItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
		        fd.setText("Load maze");
		        
		        try {
					fd.setFilterPath(new java.io.File(".").getCanonicalPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
		        
		        String[] filterExt = { "*.maz" };
		        fd.setFilterExtensions(filterExt);
		        String selected = fd.open();
		        
		        if(selected != null){
		    		setCommand(("file size "+fd.getFileName()).split(" "));
		        }
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		mazeSizeItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.setEnabled(false);
				Shell chooseShell = new Shell();
				chooseShell.setSize(350	, 250);
				chooseShell.setLayout(new GridLayout(1,false));
				chooseShell.setText("Choose maze3d");
				new Label(chooseShell, SWT.None).setText("Choose maze3d to save in file:");
						
				setCommand("mazeName".split(" "));
				String[] mazes = getMazes();
						
						
				List list = new List(chooseShell, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);    
				list.setItems(mazes);    
				list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
				
				Button saveMaze = new Button(chooseShell, SWT.PUSH);
				saveMaze.setText("Save maze");
				saveMaze.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				
				saveMaze.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						shell.setEnabled(true);
						nameCurrentMaze = mazes[list.getFocusIndex()];
						setCommand(("maze size " + nameCurrentMaze).split(" "));
						chooseShell.close();
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {}
				});
				
				//exit
				chooseShell.addListener(SWT.Close, new Listener() {
				    	@Override
				      public void handleEvent(Event event) {
				    		shell.setEnabled(true);
				      }
				 });
				
				chooseShell.open();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		loadMazeInFileItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
		        fd.setText("Load maze");
		        
		        try {
					fd.setFilterPath(new java.io.File(".").getCanonicalPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
		        
		        String[] filterExt = { "*.maz" };
		        fd.setFilterExtensions(filterExt);
		        String selected = fd.open();
		        
		        if(selected != null){
		        	String s[] = fd.getFileName().split(".maz");
		    		setCommand(("load maze "+fd.getFileName()+" "+s[0]).split(" "));
		        }
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		saveMazeInFileItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.setEnabled(false);
				Shell chooseShell = new Shell();
				chooseShell.setSize(350	, 250);
				chooseShell.setLayout(new GridLayout(2,false));
				chooseShell.setText("Choose maze3d");
				new Label(chooseShell, SWT.None).setText("Choose maze3d to save in file:");
						
				setCommand("mazeName".split(" "));
				String[] mazes = getMazes();
						
						
				List list = new List(chooseShell, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);    
				list.setItems(mazes);    
				list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
				
				Group g = new Group(chooseShell, SWT.NONE);
				g.setLayout(new GridLayout(2, false));
				g.setCapture(false);
				
				Label label = new Label(g, SWT.NONE);
				label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
				label.setText("File name:");
				
				Text fileName = new Text(g, SWT.NONE);
				fileName.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, false, 1, 1));
				fileName.setText("");
				
				Button saveMaze = new Button(chooseShell, SWT.PUSH);
				saveMaze.setText("Save maze");
				saveMaze.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				
				saveMaze.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						if(fileName.getText().equals("")==false)
						{
							setCommand(("save maze "+MainWindow.this.mazes[list.getFocusIndex()]+" "+(fileName.getText()+".maz")).split(" "));
							/*MessageBox ready = new MessageBox(chooseShell , SWT.ICON_INFORMATION | SWT.YES);
							ready.setMessage("Maze saved in file "+(fileName.getText()));
							ready.open();*/
							chooseShell.close();
						}
						else
						{
							MessageBox message = new MessageBox(chooseShell,SWT.ICON_ERROR | SWT.YES);
							message.setMessage("Please enter the file name");
							message.open();
						}
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {}
				});
				
				//exit
				chooseShell.addListener(SWT.Close, new Listener() {
				    	@Override
				      public void handleEvent(Event event) {
				    		shell.setEnabled(true);
				      }
				 });
				
				chooseShell.open();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		exitItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent event) {	
	    		shell.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		openPropertiesItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
		        fd.setText("Open Properties");
		        
		        try {
					fd.setFilterPath(new java.io.File(".").getCanonicalPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
		        
		        String[] filterExt = { "*.xml" };
		        fd.setFilterExtensions(filterExt);
		        String selected = fd.open();
		        
		        if(selected != null){
		        	XMLDecoder d;
		    		Properties properties = new Properties();
		    		try {
		    			d = new XMLDecoder(new BufferedInputStream(new FileInputStream(selected)));
		    			properties = (Properties) d.readObject();
		    			d.close();
		    		} catch (FileNotFoundException e) {
		    			e.printStackTrace();
		    		}
		    		setChanged();
		    		notifyObservers(properties);
		        }
		        	
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}				
		});
		
		aboutItem.addSelectionListener(new SelectionListener() {
			////////////////////////////////////////TODO \n???////////////////////////////////////////////////////
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MessageBox mb = new MessageBox(shell , SWT.ICON_INFORMATION | SWT.YES);
				String message = "Developers: Ben mazliach & Or moshe\n\n";
				message += "Verision: 1.0\n\n";
				message += "Contact us via e-mail:\n";
				message += "Ben - benmazliach@gmail.com, ";
				message += "Or - ormoshe2204@gmail.com";

				mb.setText("About");
				mb.setMessage(message);
				mb.open();
				}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		
		b[0].addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(mazeDisplayer.getMazeData()!=null && mazeDisplayer.isFinish()==false)
				{
					mazeDisplayer.moveUp(section);
					possibleMoves(b);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		b[1].addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(mazeDisplayer.getMazeData()!=null && mazeDisplayer.isFinish()==false)
				{
					movePageUp();
					possibleMoves(b);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		b[2].addSelectionListener(new SelectionListener() {
				
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(mazeDisplayer.getMazeData()!=null && mazeDisplayer.isFinish()==false)
				{
					mazeDisplayer.moveLeft(section);
					possibleMoves(b);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
			
		b[3].addSelectionListener(new SelectionListener() {
		
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(mazeDisplayer.getMazeData()!=null && mazeDisplayer.isFinish()==false)
				{
					mazeDisplayer.moveRight(section);					
					possibleMoves(b);
				}
			}
		
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
			
		b[4].addSelectionListener(new SelectionListener() {
				
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(mazeDisplayer.getMazeData()!=null && mazeDisplayer.isFinish()==false)
				{
					mazeDisplayer.moveDown(section);
					possibleMoves(b);
				}
			}
				
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
				
		b[5].addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(mazeDisplayer.getMazeData()!=null && mazeDisplayer.isFinish()==false)
				{
					movePageDown();
					possibleMoves(b);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0){}
		});
				
		sectionXButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(nameCurrentMaze!=null)
				{
					section = "x";
					setCommand(("display cross section by X "+((Position)mazeDisplayer.getCharacter()).getpX()+" for "+nameCurrentMaze).split(" "));
					possibleMoves(b);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		sectionYButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(nameCurrentMaze!=null)
				{
					section = "y";
					setCommand(("display cross section by Y "+((Position)mazeDisplayer.getCharacter()).getpY()+" for "+nameCurrentMaze).split(" "));
					possibleMoves(b);
					}
				}
			
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		sectionZButton.addSelectionListener(new SelectionListener() {
	
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(nameCurrentMaze!=null)
				{
					section = "z";
					setCommand(("display cross section by Z "+((Position)mazeDisplayer.getCharacter()).getpZ()+" for "+nameCurrentMaze).split(" "));
					possibleMoves(b);
				}
			}
	
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		
		generateButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.setEnabled(false);
				Shell generateShell = new Shell();
				generateShell.setSize(350, 250);
				generateShell.setLayout(new GridLayout(2,false));
				generateShell.setText("Generate maze3d");
				

				new Label(generateShell, SWT.None).setText("Maze name: ");
				Text t1 = new Text(generateShell, SWT.BORDER);
				new Label(generateShell, SWT.None).setText("X size: ");
				Text t2 = new Text(generateShell, SWT.BORDER);
				new Label(generateShell, SWT.None).setText("Y size: ");
				Text t3 = new Text(generateShell, SWT.BORDER);
				new Label(generateShell, SWT.None).setText("Z size: ");
				Text t4 = new Text(generateShell, SWT.BORDER);
				new Label(generateShell, SWT.None).setText("Generation type: ");
				String[] items = "MyMaze3dGenerator SimpleMaze3dGenerator".split(" ");
				Combo combo1 = new Combo(generateShell, SWT.DROP_DOWN);
				combo1.setItems(items);

				
				Button createMazeButton = new Button(generateShell, SWT.PUSH);
				createMazeButton.setText("Create maze");
				createMazeButton.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, false, 1, 1));
				
				createMazeButton.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						if(t1.getText().equals("")==false && t2.getText().equals("")==false &&
								t3.getText().equals("")==false && t4.getText().equals("")==false && combo1.getText().equals("")==false)
						{
							String temp = "";
							if(isInt(t2.getText())==false)
								temp = "Wrong X parameter\n";
							if(isInt(t3.getText())==false)
								temp += "Wrong Y parameter\n";
							if(isInt(t4.getText())==false)
								temp += "Wrong Z parameter\n";
							if(temp.equals(""))
							{
								String s = "generate 3d maze "+t1.getText()+" "+t2.getText()+" "+t3.getText()+" "+t4.getText()+" "+combo1.getText();
								String[] args = s.split(" ");
								setCommand(args);
								/*MessageBox ready = new MessageBox(generateShell , SWT.ICON_INFORMATION | SWT.YES);
								ready.setMessage("Maze saved");
								ready.open();*/
								generateShell.close();
							}
							else
							{
								MessageBox wrong = new MessageBox(generateShell , SWT.ICON_ERROR | SWT.YES);
								wrong.setMessage(temp);
								wrong.open();
							}
								
						}
						else
						{
							MessageBox wrong = new MessageBox(generateShell , SWT.ICON_ERROR | SWT.YES);
							wrong.setMessage("Wrong parameters!!!");
							wrong.open();
						}
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {}
				});
				
				//exit
				generateShell.addListener(SWT.Close, new Listener() {
				    	@Override
				      public void handleEvent(Event event) {
				    		shell.setEnabled(true);
				      }
				 });
				generateShell.open();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
				
		displayMazeButton.addSelectionListener(new SelectionListener() {
					
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.setEnabled(false);
				Shell chooseShell = new Shell();
				chooseShell.setSize(350, 250);
				chooseShell.setLayout(new GridLayout(1,true));
				chooseShell.setText("Choose maze3d");
				new Label(chooseShell, SWT.None).setText("Choose maze3d to be displayed:");
						
				setCommand("mazeName".split(" "));
				String[] mazes = getMazes();
						
						
				List list = new List(chooseShell, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);    
				list.setItems(mazes);    
				list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
				
				Button displayMazeButton = new Button(chooseShell, SWT.PUSH);
				displayMazeButton.setText("Dispaly maze");
				displayMazeButton.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false, 1, 1));
				
				displayMazeButton.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						shell.setEnabled(true);
						section = "y";
						mazeDisplayer.setSol(null);
						mazeDisplayer.setFinish(false);
						nameCurrentMaze = mazes[list.getFocusIndex()];
						setCommand(("display " + nameCurrentMaze).split(" "));
						possibleMoves(b);
						chooseShell.close();
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {}
				});
				
				chooseShell.open();
				
				//exit
				chooseShell.addListener(SWT.Close, new Listener() {
			       	  @Override
				      public void handleEvent(Event event) {
				    		shell.setEnabled(true);
				      }
				 });
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		solButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(nameCurrentMaze!=null)
				{
					Position temp = maze.getStartPosition();
					maze.setStartPosition((Position)mazeDisplayer.getCharacter());
					setCommand("solveAlgorithm".split(" "));
					setCommand(("solve "+nameCurrentMaze+" "+solveAlg).split(" "));
					maze.setStartPosition(temp);
					setCommand(("display solution "+nameCurrentMaze).split(" "));
					timer=new Timer();
					task=new TimerTask() {
						@Override
						public void run() {
							display.syncExec(new Runnable() {
								@Override
								public void run() {
									Walk(mazeDisplayer.getSol());
								}
							});
						}
					};				
					timer.scheduleAtFixedRate(task, 0, 500);
				}
				else
				{
					MessageBox message = new MessageBox(shell,SWT.ICON_ERROR| SWT.YES);
					message.setMessage("You have to choose a maze first!!!");
					message.open();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		solveButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(nameCurrentMaze!=null)
				{
					Position temp = maze.getStartPosition();
					maze.setStartPosition((Position)mazeDisplayer.getCharacter());
					setCommand("solveAlgorithm".split(" "));
					setCommand(("solve "+nameCurrentMaze+" "+solveAlg).split(" "));
					setCommand(("display solution "+nameCurrentMaze).split(" "));
					maze.setStartPosition(temp);
				}
				else
				{
					MessageBox message = new MessageBox(shell,SWT.ICON_ERROR| SWT.YES);
					message.setMessage("You have to choose a maze first!!!");
					message.open();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		hintButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(mazeDisplayer.getMazeData()!=null)
				{
					Position temp = maze.getStartPosition();
					maze.setStartPosition((Position)mazeDisplayer.getCharacter());
					setCommand(("hint "+nameCurrentMaze).split(" "));
					maze.setStartPosition(temp);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		mazeDisplayer.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(mazeDisplayer.isFinish()==false)
				{
					switch(arg0.keyCode)
					{
					case SWT.ARROW_UP: mazeDisplayer.moveUp(section);
							break;
					case SWT.ARROW_DOWN: mazeDisplayer.moveDown(section);
							break;
					case SWT.ARROW_LEFT: mazeDisplayer.moveLeft(section);
							break;
					case SWT.ARROW_RIGHT: mazeDisplayer.moveRight(section);
							break;
					case SWT.PAGE_UP: movePageUp();
							break;
					case SWT.PAGE_DOWN: movePageDown();
							break;
					}
				}
				possibleMoves(b);
			}
		});
		
		//exit
		shell.addListener(SWT.Close, new Listener() {
	       	  @Override
		      public void handleEvent(Event event) {
		    		MessageBox messageBox = new MessageBox(shell,SWT.ICON_QUESTION| SWT.YES | SWT.NO);
		    		messageBox.setMessage("Do you really want to exit?");
		    		if(event.doit = messageBox.open () == SWT.YES)
		    			setCommand("exit".split(" "));
		      }
		 });
	}
	
	/**
	 * Runnable's start
	 */
	@Override
	public void start() {
		run();
	}
	
	/**
	 * Move the character to the below section
	 */
	public void movePageUp()
	{
		if(section.equals("y")==true)
		{
			int pY = ((Position)mazeDisplayer.getCharacter()).getpY()+1;
			if(maze.getYSize()>pY)
			{
				if(maze.returnValue(((Position)mazeDisplayer.getCharacter()).getpX(), pY, ((Position)mazeDisplayer.getCharacter()).getpZ())==0)
				{
					((Position)mazeDisplayer.getCharacter()).setpY(pY);
					setCommand(("display cross section by Y "+pY+" for "+nameCurrentMaze).split(" "));
				}
			}
		}
		else if(section.equals("x")==true)
		{
			int pX = ((Position)mazeDisplayer.getCharacter()).getpX()+1;
			if(maze.getYSize()>pX)
			{
				if(maze.returnValue(pX, ((Position)mazeDisplayer.getCharacter()).getpY(), ((Position)mazeDisplayer.getCharacter()).getpZ())==0)
				{
					((Position)mazeDisplayer.getCharacter()).setpX(pX);
					setCommand(("display cross section by X "+pX+" for "+nameCurrentMaze).split(" "));
				}
			}
		}
		if(section.equals("z")==true)
		{
			int pZ = ((Position)mazeDisplayer.getCharacter()).getpZ()+1;
			if(maze.getYSize()>pZ)
			{
				if(maze.returnValue(((Position)mazeDisplayer.getCharacter()).getpX(), ((Position)mazeDisplayer.getCharacter()).getpY(), pZ)==0)
				{
					((Position)mazeDisplayer.getCharacter()).setpZ(pZ);
					setCommand(("display cross section by Z "+pZ+" for "+nameCurrentMaze).split(" "));
				}
			}
		}
	}
	
	/**
	 * Move the character to the upper section
	 */
	public void movePageDown()
	{
		if(section.equals("y")==true)
		{
			int pY = ((Position)mazeDisplayer.getCharacter()).getpY()-1;
			if(pY>=0)
			{
				if(maze.returnValue(((Position)mazeDisplayer.getCharacter()).getpX(), pY, ((Position)mazeDisplayer.getCharacter()).getpZ())==0)
				{
					((Position)mazeDisplayer.getCharacter()).setpY(pY);
					setCommand(("display cross section by Y "+pY+" for "+nameCurrentMaze).split(" "));
				}
			}
		}
		else if(section.equals("x")==true)
		{
			int pX = ((Position)mazeDisplayer.getCharacter()).getpX()-1;
			if(pX>=0)
			{
				if(maze.returnValue(pX, ((Position)mazeDisplayer.getCharacter()).getpY(), ((Position)mazeDisplayer.getCharacter()).getpZ())==0)
				{
					((Position)mazeDisplayer.getCharacter()).setpX(pX);
					setCommand(("display cross section by X "+pX+" for "+nameCurrentMaze).split(" "));
				}
			}
		}
		if(section.equals("z")==true)
		{
			int pZ = ((Position)mazeDisplayer.getCharacter()).getpZ()-1;
			if(pZ>=0)
			{
				if(maze.returnValue(((Position)mazeDisplayer.getCharacter()).getpX(), ((Position)mazeDisplayer.getCharacter()).getpY(), pZ)==0)
				{
					((Position)mazeDisplayer.getCharacter()).setpZ(pZ);
					setCommand(("display cross section by Z "+pZ+" for "+nameCurrentMaze).split(" "));
				}
			}
		}
	}
	
	/**
	 * Open Messagebox
	 */
	@Override
	public void displayString(String s) {
		if(isInt(s)==true)
		{
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION| SWT.YES);
			messageBox.setMessage("You need to do "+s+" steps to treasure");
			messageBox.open();
		}
		else
		{
			display.asyncExec(new Runnable() {
				
				@Override
				public void run() {
					//System.out.println(s);
					if(s.equals("Exit")==false && s.contains("solution")==false && s.contains("Solution")==false)
					{
						MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION| SWT.YES);
						messageBox.setMessage(s);
						messageBox.open();
					}
				}
			});
		}
	}
	
	/**
	 * Print the solution
	 */
	@Override
	public void displaySolution(Solution<Position> sol, String name) {
		ArrayList<State<Position>> temp = new ArrayList<State<Position>>();
		for(int i=sol.getSol().size()-1;i>=0;i--)
		{
			temp.add(sol.getSol().get(i));
		}
		
		mazeDisplayer.setSol(new Solution<Position>(temp));
		mazeDisplayer.draw(section);
		mazeDisplayer.redraw();
	}
	
	/**
	 * Get the maze
	 * @return Maze3d
	 */
	public Maze3d getMaze() {
		return maze;
	}

	/**
	 * Set the maze
	 * @param Maze3d
	 */
	public void setMaze(Maze3d maze) {
		this.maze = maze;
	}

	/**
	 * Display the maze
	 */
	@Override
	public void displayMaze3d(Maze3d maze, String name) {
		this.setMaze(maze);
		mazeDisplayer.setCharacter(maze.getStartPosition());
		mazeDisplayer.setGoalPosition(maze.getGoalPosition());
		mazeDisplayer.setMazeData(maze.getCrossSectionByY(((Position)mazeDisplayer.getCharacter()).getpY()));
		mazeDisplayer.draw(section);
		mazeDisplayer.redraw();
	}

	
	/**
	 * NotifyObservers
	 */
	@Override
	public void setCommand(String[] args) {
		this.args = args;
		setChanged();
		notifyObservers();
	}

	/**
	 * Set the args
	 */
	@Override
	public void setArgs(String[] args) {
		this.args = args;
	}

	/**
	 * Get the args
	 */
	@Override
	public String[] getArgs() {
		return this.args;
	}

	/**
	 * Display the cross section (x/y/z)
	 * @param arr
	 * @param sectionType
	 * @param name
	 * @param section
	 */
	@Override
	public void displayCrossSection(int[][] arr, String sectionType, String name, String section) {
		mazeDisplayer.setMazeData(arr);
		mazeDisplayer.draw(this.section);
		mazeDisplayer.redraw();
	}

	/**
	 * Get all the mazes by name
	 * @return String[]
	 */
	@Override
	public String[] getMazes() {
		return mazes;
	}

	/**
	 * Set all the mazes by name
	 * @param String[]
	 */
	@Override
	public void setMazes(String[] mazes) {
		this.mazes = mazes;
	}
	
	/**
	 * Insert the possible moves picuters into the group of the possible moves
	 * @param Button[] b
	 */
	public void possibleMoves(Button[] b)
	{
		String[] possibleMoves = null;
		
		if(mazeDisplayer.getMazeData()!=null)
		{
			possibleMoves = mazeDisplayer.possibleMoves(section);
			String str = "";
			
			for (String string : possibleMoves) {
				str+= (string+" ");
			}
			
			//   Down / Up
			int h = 0;
			int up = 1;
			int down = 1;
			int maxSection = 0;
			if(section.equals("y")==true)
			{
				h = ((Position)mazeDisplayer.getCharacter()).getpY();
				maxSection = maze.getYSize();
				if(h+1<maxSection)
					up = maze.returnValue(((Position)mazeDisplayer.getCharacter()).getpX(), h+1, ((Position)mazeDisplayer.getCharacter()).getpZ());
				if(h-1>=0)
					down = maze.returnValue(((Position)mazeDisplayer.getCharacter()).getpX(), h-1, ((Position)mazeDisplayer.getCharacter()).getpZ());
			}
			else if(section.equals("x")==true)
			{
				h = ((Position)mazeDisplayer.getCharacter()).getpX();
				maxSection = maze.getXSize();
				if(h+1<maxSection)
					up = maze.returnValue(h+1,((Position)mazeDisplayer.getCharacter()).getpY(),  ((Position)mazeDisplayer.getCharacter()).getpZ());
				if(h-1>=0)
					down = maze.returnValue(h-1, ((Position)mazeDisplayer.getCharacter()).getpY(), ((Position)mazeDisplayer.getCharacter()).getpZ());
			}
			else if(section.equals("z")==true)
			{
				h = ((Position)mazeDisplayer.getCharacter()).getpZ();
				maxSection = maze.getZSize();
				if(h+1<maxSection)
					up = maze.returnValue(((Position)mazeDisplayer.getCharacter()).getpX(), ((Position)mazeDisplayer.getCharacter()).getpY(), h+1);
				if(h-1>=0)
					down = maze.returnValue(((Position)mazeDisplayer.getCharacter()).getpX(), ((Position)mazeDisplayer.getCharacter()).getpY(), h-1);
			}
			else
				return;
			
			if(up==0)
				str+="Up ";
			if(down==0)
				str+="Down ";
			
			possibleMoves = str.split(" ");
		}
		
		b[0].setImage(new Image(display, "resources/backward1.png"));
		b[1].setImage(new Image(display, "resources/UP1.png"));
		b[2].setImage(new Image(display, "resources/left1.png"));
		b[3].setImage(new Image(display, "resources/right1.png"));
		b[4].setImage(new Image(display, "resources/forward1.png"));
		b[5].setImage(new Image(display, "resources/DOWN1.png"));
		
		if(possibleMoves!=null && mazeDisplayer.isFinish()==false)
		{
			for(int i=0;i<possibleMoves.length;i++)
			{
				if(possibleMoves[i].equals("Backward")==true)
					b[0].setImage(new Image(display, "resources/backward2.png"));
				else if(possibleMoves[i].equals("Up")==true)
					b[1].setImage(new Image(display, "resources/UP2.png"));
				else if(possibleMoves[i].equals("Left")==true)
					b[2].setImage(new Image(display, "resources/left2.png"));
				else if(possibleMoves[i].equals("Right")==true)
					b[3].setImage(new Image(display, "resources/right2.png"));
				else if(possibleMoves[i].equals("Forward")==true)
					b[4].setImage(new Image(display, "resources/forward2.png"));
				else if(possibleMoves[i].equals("Down")==true)
					b[5].setImage(new Image(display, "resources/DOWN2.png"));
			}
		}
		
	}
	
	/**
	 * True if the string is built from numbers
	 * @param String str
	 * @return boolean
	 */
	public boolean isInt(String str) {
		for (int i = 0; i < str.length(); i++) {
			if(str.charAt(i)<'0' || str.charAt(i)>'9')
				return false;
		}
		return true;
	}

	/**
	 * Get the solve algorithm
	 * @return String
	 */
	@Override
	public String getSolveAlg() {
		return solveAlg;
	}

	/**
	 * Get the solve algorithm
	 * @param String
	 */
	@Override
	public void setSolveAlg(String solveAlg) {
		this.solveAlg = solveAlg;
	}

	/**
	 * Walk in the  solution steps to the goal position
	 * @param Solution<.Position.> sol
	 */
	private void Walk(Solution<Position> sol){
		if(sol.getSol().size()>0)
		{
			//y
			int w1 = sol.getSol().get(0).getState().getpX();
			int f1 = sol.getSol().get(0).getState().getpY();
			int h1 = sol.getSol().get(0).getState().getpZ();
			int w2 = ((Position)mazeDisplayer.getCharacter()).getpX();
			int f2 = ((Position)mazeDisplayer.getCharacter()).getpY();
			int h2 = ((Position)mazeDisplayer.getCharacter()).getpZ();
			
			if(section.equals("x")==true)
			{
				f1 = sol.getSol().get(0).getState().getpX();
				h1 = sol.getSol().get(0).getState().getpY();
				w1 = sol.getSol().get(0).getState().getpZ();
				w2 = ((Position)mazeDisplayer.getCharacter()).getpZ();
				f2 = ((Position)mazeDisplayer.getCharacter()).getpX();
				h2 = ((Position)mazeDisplayer.getCharacter()).getpY();
			}
			else if(section.equals("z")==true)
			{
				w1 = sol.getSol().get(0).getState().getpX();
				h1 = sol.getSol().get(0).getState().getpY();
				f1 = sol.getSol().get(0).getState().getpZ();
				w2 = ((Position)mazeDisplayer.getCharacter()).getpX();
				f2 = ((Position)mazeDisplayer.getCharacter()).getpZ();
				h2 = ((Position)mazeDisplayer.getCharacter()).getpY();
			}
			
			if(w2>w1)
				mazeDisplayer.moveLeft(this.section);
			else if(w2<w1)
				mazeDisplayer.moveRight(this.section);
			else if(h2>h1)
				mazeDisplayer.moveUp(this.section);
			else if(h2<h1)
				mazeDisplayer.moveDown(this.section);
			else if(f2>f1)
				movePageDown();
			else if(f2<f1)
				movePageUp();
			
			sol.getSol().remove(0);
		}
		else
			timer.cancel();
	}
	
}
