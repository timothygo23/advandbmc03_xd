package controller;

import javafx.stage.Stage;
import view.AllRegionView;
import view.AsiaAfricaView;
import view.ConcreteView;
import view.EuropeAmericaView;
import view.NodeChooseView;
import view.NodeView;

public class MainController extends Controller{
	public static final int ALL_REGION_VIEW = 1;
	public static final int EUROPE_AMERICA_VIEW = 2;
	public static final int ASIA_AFRICA_VIEW = 3;
	public static final int NODES_VIEW = 4;
	
	public MainController(Stage stage) {
		super(stage);
		mainStage.setTitle("ADVANDB MCO2");
		scene.getStylesheets ().add ("./StyleSheet.css");
		
		setScene(NODES_VIEW);
	}

	@Override
	protected void initViews() {
		
	}

	@Override
	protected void changeView() {
		switch (currentView) {
			case NODES_VIEW:
				NodeChooseView ncv = new NodeChooseView(this);
				scene.setRoot(ncv);
				break;
			case ALL_REGION_VIEW:
				AllRegionView arv = new AllRegionView(this);
				scene.setRoot(arv);
				break;
			case EUROPE_AMERICA_VIEW:
				EuropeAmericaView eav = new EuropeAmericaView(this);
				scene.setRoot(eav);
				break;
			case ASIA_AFRICA_VIEW:
				AsiaAfricaView aav = new AsiaAfricaView(this);
				scene.setRoot(aav);
				break;
		}
		
	}

	@Override
	public void setScene(int n) {
		currentView = n;
		changeView ();
	}
	
}
