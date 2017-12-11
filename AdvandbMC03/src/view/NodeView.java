package view;

import java.util.ArrayList;

import controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public abstract class NodeView extends BorderPane implements View{
	
	protected final String LOCAL = "Local";
	protected final String GLOBAL = "Global";
	
	protected ObservableList<ObservableList> data;
	
	protected ArrayList<TableColumn> col;
	protected ObservableList<String> row;

	protected MainController mc;
	
	protected HBox topHBox;
		protected HBox leftHBox;
			protected VBox nodeVBox;
				protected Label nodeLabel;
				protected Label replicaLabel;
		protected HBox rightHBox;
			protected VBox leftTopVBox;
				protected ComboBox<String> concurrencyTypeComboBox;
				protected HBox repositoryHBox;
					protected Button mainButton;
					protected Button replicaButton;
			protected VBox rightTopVBox;
				protected ComboBox<String> statementComboBox;
				protected Button simulateButton;
				
	protected VBox centerVBox;
		protected Label previewLabel;
		protected TextArea previewTextArea;
		protected Label resultsLabel;
		protected ResultsTable resultsTable;
		
	protected VBox rightVBox;
		private HBox itazuraHBox;
			private ImageView itazuraView;
			private Image itazura;
		protected Label logLabel;
		protected TextArea logTextArea;
		protected Button clearLogButton;
	
	public NodeView (MainController mc) {
		super ();
		this.mc = mc;
		col = new ArrayList<TableColumn>();
		data = FXCollections.observableArrayList();
		initPane ();
	}
	
	private void initPane() {
		setMaxSize (Double.MAX_VALUE, Double.MAX_VALUE);
		initTop();
		initCenter();
		initRight();
		
		setTop(topHBox);
		//setLeft(leftVBox);
		setCenter(centerVBox);
		setRight(rightVBox);
	}

	private void initTop() {
		topHBox = new HBox ();
		topHBox.setId("TopHbox");
		
			leftHBox = new HBox ();
			leftHBox.setAlignment(Pos.CENTER_LEFT);
			
				nodeVBox = new VBox ();
				nodeVBox.setAlignment(Pos.CENTER_LEFT);
				
					nodeLabel = new Label ("Node");
					nodeLabel.setId("DefaultLabel");
					
					replicaLabel = new Label ("Replica");
					replicaLabel.setId("DefaultLabel");
				
				nodeVBox.getChildren().addAll(nodeLabel, replicaLabel);
			
			leftHBox.getChildren().addAll(nodeVBox);
			
			rightHBox = new HBox (10);
			rightHBox.setAlignment(Pos.CENTER_RIGHT);
			
				leftTopVBox = new VBox (10);
				leftTopVBox.setAlignment(Pos.CENTER_RIGHT);
				
					concurrencyTypeComboBox = new ComboBox<String> ();
					concurrencyTypeComboBox.getStyleClass().add("ComboBox");
					concurrencyTypeComboBox.getItems().add("Local");
					concurrencyTypeComboBox.getItems().add("Global");
					concurrencyTypeComboBox.getSelectionModel().selectFirst();
					
					repositoryHBox = new HBox (10);
					
						mainButton = new Button ("Main");
						mainButton.getStyleClass().add("Button");
						
						replicaButton = new Button ("Replica");
						replicaButton.getStyleClass().add("Button");
						
					repositoryHBox.getChildren().addAll(mainButton, replicaButton);
				
				leftTopVBox.getChildren().addAll(concurrencyTypeComboBox, repositoryHBox);
				
				rightTopVBox = new VBox (10);
				rightTopVBox.setAlignment(Pos.CENTER_LEFT);
				
					statementComboBox = new ComboBox<String> ();
					statementComboBox.getStyleClass().add("ComboBox");
					statementComboBox.getItems().add("Case 1");
					statementComboBox.getItems().add("Case 2");
					statementComboBox.getItems().add("Case 3");
					statementComboBox.getSelectionModel().selectFirst();
					
					simulateButton = new Button ("Simulate");
					simulateButton.getStyleClass().add("GreenButton");
				
				rightTopVBox.getChildren().addAll(statementComboBox, simulateButton);
				
			rightHBox.getChildren().addAll(leftTopVBox, rightTopVBox);
		
		topHBox.getChildren().addAll(leftHBox, rightHBox);
		
		HBox.setHgrow(leftHBox, Priority.ALWAYS);
		HBox.setHgrow(rightHBox, Priority.ALWAYS);
	}

	private void initCenter() {
		centerVBox = new VBox (10);
		centerVBox.setId("CenterVBox");
		
			previewLabel = new Label ("Preview");
			previewLabel.setId("DefaultLabel");
			
			previewTextArea = new TextArea ();
			previewTextArea.setEditable(false);
			
			resultsLabel = new Label ("Results");
			resultsLabel.setId("DefaultLabel");
			
			resultsTable = new ResultsTable (mc);
		
		centerVBox.getChildren().addAll(previewLabel, previewTextArea, resultsLabel, resultsTable);
	}

	private void initRight() {
		rightVBox = new VBox (10);
		rightVBox.setId("RightVBox");
		rightVBox.setAlignment(Pos.CENTER);

			itazuraHBox = new HBox ();
			
				itazura = new Image(("itazura.jpg"));
				itazuraView = new ImageView(); 
				itazuraView.setImage(itazura);
				itazuraView.setPreserveRatio(true);

			itazuraHBox.getChildren().addAll(itazuraView);
		
			logLabel = new Label ("Log");
			logLabel.setId("DefaultLabel");
			
			logTextArea = new TextArea ();
			logTextArea.setEditable(false);
			
			clearLogButton = new Button ("Clear Log");
			clearLogButton.getStyleClass().add("RedButton");
		
		rightVBox.getChildren().addAll(itazuraHBox, logLabel, logTextArea, clearLogButton);
		
		VBox.setVgrow(rightVBox, Priority.ALWAYS);
	}
	
	@Override
	public void update() {
		
	}
	
	public void changePreviewTextArea(String preview){
		previewTextArea.setText(preview);
	}
	
	public void appendLogText(String text){
		logTextArea.setText(logTextArea.getText() + "\n" + text);
	}
	
	protected abstract void setLabels();

}
