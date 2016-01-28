package Presenter;

import View.View;
import model.Model;

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
