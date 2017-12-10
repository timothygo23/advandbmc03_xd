package view;

import controller.MainController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NodeChooseView extends BorderPane implements View{
	private MainController mc;
	
	private VBox centerVBox;
		private Button allRegion;
		private Button europeAmerica;
		private Button asiaAfrica;
	
	public NodeChooseView(MainController mc){
		super();
		this.mc = mc;
		initScene();
	}
	
	private void initScene() {
		setMaxSize (Double.MAX_VALUE, Double.MAX_VALUE);
		init();
		initHandler();
		setCenter (centerVBox);
	}
	
	public void init(){
		//center box
		centerVBox = new VBox();
		centerVBox.setId("centerVBox");
		centerVBox.setSpacing(10);
		centerVBox.setAlignment (Pos.CENTER);
		
		allRegion = new Button("ALL REGION NODE");
		allRegion.getStyleClass().add("Button");
		
		europeAmerica = new Button("EUROPE AND AMERICA");
		europeAmerica.getStyleClass().add("Button");
		
		asiaAfrica = new Button("ASIA AND AFRICA");
		asiaAfrica.getStyleClass().add("Button");
		
		centerVBox.getChildren (). addAll(allRegion, europeAmerica, asiaAfrica);

	}
	
	public void initHandler(){
		allRegion.setOnAction(e -> {
			mc.setScene(MainController.ALL_REGION_VIEW);
		});
		
		europeAmerica.setOnAction(e -> {
			mc.setScene(MainController.EUROPE_AMERICA_VIEW);
		});
		
		asiaAfrica.setOnAction(e -> {
			mc.setScene(MainController.ASIA_AFRICA_VIEW);
		});
	}

	@Override
	public void update() {
		
	}

}
