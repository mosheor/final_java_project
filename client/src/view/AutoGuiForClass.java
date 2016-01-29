package view;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import presenter.Properties;

/**
 * <h1>  class AutoGuiForClass <h1>
 * This class creates a generic GUI window for any class
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   28/01/16
 */
public class AutoGuiForClass extends BasicWindow{

	private boolean changeSucceeded = false;
	Object newCreatedClass;
	@SuppressWarnings("rawtypes")
	Class classType;
	boolean success = false;
	
	
	/**
	 * Default c'tor
	 * @param title
	 * @param classType
	 * @param x
	 * @param y
	 */
	@SuppressWarnings("rawtypes")
	public AutoGuiForClass(String title, Class classType ,int x,int y) {
		super(title, x, y);
		this.classType = classType;
	}

	public static void main(String[] args) {
		Properties p = new Properties();
		AutoGuiForClass gui= new AutoGuiForClass("maze example", p.getClass(),300,400);
		gui.run();
		p = (Properties)gui.getNewCreatedClass();

		try {
			FileOutputStream file = new FileOutputStream("properties.xml");
			BufferedOutputStream bos = new BufferedOutputStream(file);
			XMLEncoder s = new XMLEncoder(bos);
			s.writeObject(p);
			s.flush();
			s.close();
			file.close();
			bos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Override the initWidgent from BasicWindow to insert the widgets for the GUI
	 */
	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2,false));
		shell.setBackground(new Color(display, 100, 200, 225));
		
		Label title = new Label(shell,SWT.LEFT);		
		title.setText(classType.getSimpleName()+":");
		title.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		title.setBackground(new Color(display, 100, 200, 225));
		title.setBackground(new Color(display, 100, 200, 225));
		
		Field[] fields= classType.getDeclaredFields();
		HashMap<String,Text> values = new HashMap<String, Text>();
		
		for(Field f : fields){
			
			Label label = new Label(shell, SWT.READ_ONLY);
			label.setBackground(new Color(display, 100, 200, 225));
			label.setText(f.getName());
			label.setLayoutData(new GridData(SWT.FILL ,SWT.TOP ,false ,false ,1 ,1));
			label.setBackground(new Color(display, 100, 200, 225));
			
			values.put(f.getName(), new Text(shell, SWT.BORDER));
			values.get(f.getName()).setText("");
			values.get(f.getName()).setLayoutData(new GridData(SWT.FILL ,SWT.TOP ,false ,false ,1 ,1));
		}
		            
		Button saveButton = new Button(shell, SWT.PUSH);
		saveButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		saveButton.setText("Save");
		saveButton.addSelectionListener( new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				try {
					newCreatedClass = classType.newInstance();
					for(Field field : fields){
						field.setAccessible(true);
						
						if(!(values.get(field.getName()).getText()).equals("")){
							if (!field.getType().equals(String.class)) {
								field.set(newCreatedClass, Integer.parseInt(values.get(field.getName()).getText()));
							} 
							else {
								field.set(newCreatedClass, values.get(field.getName()).getText());
							}
						}
						else{
							setEror("All fields sould filled!");
							return;
						}
					}
					
					changeSucceeded = true;
					
				} catch (InstantiationException | IllegalAccessException e) {

					e.printStackTrace();
				}
				
				shell.dispose();
				}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
	
		//exit
		shell.addListener(SWT.Close, new Listener() {
	       	  @Override
		      public void handleEvent(Event event) {
		    		MessageBox messageBox = new MessageBox(shell,SWT.ICON_QUESTION| SWT.YES | SWT.NO);
		    		messageBox.setMessage("Do you really want to exit?");
		    		if(event.doit = messageBox.open () == SWT.YES);
		      }
		 });
			
	}
	
	/**
	 * @return success - if the create succeeded
	 */
	public boolean success() {
		return success;
	}

	/**
	 * @return the new object that created
	 */
	public Object getNewCreatedClass() {
		return newCreatedClass;
	}

	/**
	 * Return true if the creation of the class succeeded
	 * @return boolean
	 */
	public boolean isChangeSucceeded() {
		return changeSucceeded;
	}
	
	/**
	 * Display error MessageBox
	 * @param eror
	 */
	private void setEror(String eror){
		Display.getCurrent().syncExec(new Runnable() {
			
			@Override
			public void run() {
				MessageBox errorBox =  new MessageBox(shell, SWT.ICON_ERROR); 
				errorBox.setMessage(eror);
				errorBox.setText("Error");
				errorBox.open();				
			}
		});
	}
}
