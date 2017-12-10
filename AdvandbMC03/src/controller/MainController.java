package controller;

import javafx.stage.Stage;
import view.ConcreteView;

public class MainController extends Controller{

	private ConcreteView cv;
	
	public MainController(Stage stage) {
		super(stage);
		mainStage.setTitle("ADVANDB MCO2");
		scene.getStylesheets ().add ("./StyleSheet.css");
	}

	@Override
	protected void initViews() {
		cv = new ConcreteView(this);	
	}

	@Override
	protected void changeView() {
		switch (currentView) {
			case 0:
				scene.setRoot (cv.getView ());
				break;
				
			default:
				scene.setRoot (cv.getView ());
		}
		
	}

	@Override
	public void setScene(int n) {
		switch (n) {
			case 0:
			case 1:
				currentView = n;
				break;
				
			default:
				currentView = 0;
		}
		
		changeView ();
	}
	
}
