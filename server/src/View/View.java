package View;

/**
 * <h1>  View Interface <h1>
 * This interface manage the view for the server
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   24/01/16
 */
public interface View {
	
	/**
	 * Open a messagebox
	 * @param String str
	 */
	public void displayString(String str);
	
	/**
	 * Display the client status in the list
	 * @param string str
	 */
	public void displayClient(String str);

}
