package controller;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Controller {

	protected Stage mainStage;
	protected Scene scene;
	protected int currentView;
	
	public Controller (Stage stage) {
		this.mainStage = stage;
		
		scene = new Scene (new Group (), mainStage.getWidth (), mainStage.getHeight ());
		initViews ();
		
		setScene (0);
		
		stage.setScene (scene);
		stage.show ();
	}
	
	protected abstract void initViews ();
	
	protected abstract void changeView ();
	
	public abstract void setScene (int n);
	
	public Stage getStage () {
		return mainStage;
	}
	
}
