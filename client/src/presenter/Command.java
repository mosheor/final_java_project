package presenter;

/**
 * <h1>  Command Interface <h1>
 * This interface define the commands we create
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   17/01/16
 */
public interface Command {

	/**
	 * Define which arguments the method should except
	 * @param String[] s
	 */
	 void doCommand(String[] args);
	
}
