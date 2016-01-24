package presenter;

import model.Model;
import view.View;

/**
 * <h1>  Presenter Interface <h1>
 * This interface manages all the program between the model and view
 * 
 * @author  Ben Mazliach & Or Moshe
 * @version 1.0
 * @since   18/1/16
 */
public interface Presenter {
	
	/**
	* Set model
	* @param Model model
	*/
	void setModel(Model model);
	
	/**
	* Set view
	* @param View view
	*/
	void setView(View view);
	
}
