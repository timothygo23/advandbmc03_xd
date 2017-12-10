package view;

import java.sql.ResultSet;
import java.util.ArrayList;

import controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PreviewTable extends HBox implements View{

	private MainController mc;
	
	private TableView tableView;
	private ObservableList<ObservableList> data;
	
	private ArrayList<TableColumn> col;
	private ObservableList<String> row;
	
	private ResultSet rs = null;
	
	public PreviewTable (MainController mc) {
		super ();
		
		this.mc = mc;
		
		initPT ();
	}
	
	private void initPT() {
		tableView = new TableView();
		//tableView.setId("TableView");
		col = new ArrayList<TableColumn>();
		data = FXCollections.observableArrayList();
		
		setHgrow(tableView, Priority.ALWAYS);
		getChildren().addAll(tableView);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
