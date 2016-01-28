package View;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import Presenter.Properties;

public class AutoGuiForClass extends BasicWindow{

	private boolean changeSucceeded = false;
	Object newCreatedClass;
	@SuppressWarnings("rawtypes")
	Class classType;
	boolean success = false;
	
	
	@SuppressWarnings("rawtypes")
	public AutoGuiForClass(String title, Class classType ,int x,int y) {
		super(title, x, y);
		this.classType = classType;
	}

	public static void main(String[] args) {
		Properties p = new Properties();
		AutoGuiForClass gui= new AutoGuiForClass("maze example", p.getClass(),300,400);
		gui.run();
		//p = (Properties)gui.getNewCreatedClass();
		//System.out.println(p.getNumOfClients());
		//System.out.println(p.getPort());
	}

	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(3,false));
		
		Label titel = new Label(shell,SWT.LEFT);		
		titel.setText(classType.getSimpleName()+":");
		titel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1));
		
		Field[] fields= classType.getDeclaredFields();
		HashMap<String,Text> values = new HashMap<String, Text>();
		
		for(Field f : fields){
			
			Label lable = new Label(shell, SWT.READ_ONLY);
			lable.setText(f.getName());
			lable.setLayoutData(new GridData(SWT.FILL ,SWT.TOP ,false ,false ,1 ,1));
			
			values.put(f.getName(), new Text(shell, SWT.BORDER));
			values.get(f.getName()).setText("");
			values.get(f.getName()).setLayoutData(new GridData(SWT.FILL ,SWT.TOP ,false ,false ,1 ,1));
			
			Label lable1 = new Label(shell, SWT.READ_ONLY);
			lable1.setText(f.getName().getClass().toString());
			lable1.setLayoutData(new GridData(SWT.FILL ,SWT.TOP ,false ,false ,1 ,1));
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


	public boolean isChangeSucceeded() {
		return changeSucceeded;
	}


	public void setChangeSucceeded(boolean changeSucceeded) {
		this.changeSucceeded = changeSucceeded;
	}
	
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
