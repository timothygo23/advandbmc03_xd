package view;

import controller.MainController;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class ConcreteView extends BorderPane implements View{
	private MainController mc;

	public ConcreteView (MainController mc) {
		super ();
		this.mc = mc;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public Parent getView() {
		return this;
	}

}
