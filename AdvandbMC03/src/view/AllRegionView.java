package view;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JTable;

import constants.MySqlStatement;
import controller.MainController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import network.Node;
import util.Transaction;

public class AllRegionView extends NodeView {
	private final String mainRepo = "ALL REGIONS";
	private final String replicaRepo = "ASIA AFRICA";
	
	private Node node = new Node(Node.BOTH_NODE_NUMBER);

	public AllRegionView(MainController mc) {
		super(mc);
		initHandler();
		setLabels();
	}
	
	public void initHandler(){
		changePreviewTextArea("Transaction 1: \n" + 
				MySqlStatement.localCase1_Transaction1() + 
				"\nTransaction 2: \n" +
				MySqlStatement.localCase1_Transaction2());
		statementComboBox.setOnAction(e -> {
			if(concurrencyTypeComboBox.getSelectionModel().getSelectedItem().equals(LOCAL)){
				//local queries
				switch(statementComboBox.getSelectionModel().getSelectedIndex()){
					case 0:	changePreviewTextArea("Transaction 1: \n" + 
							MySqlStatement.localCase1_Transaction1() + 
							"\nTransaction 2: \n" +
							MySqlStatement.localCase1_Transaction2());
						break;
					case 1: changePreviewTextArea("Transaction 1: \n" + 
							MySqlStatement.localCase2_Transaction1() + 
							"\nTransaction 2: \n" +
							MySqlStatement.localCase2_Transaction2("AFG"));
						break;
					case 2: changePreviewTextArea("Transaction 1: \n" + 
							MySqlStatement.localCase3_Transaction1("PHL") + 
							"\nTransaction 2: \n" +
							MySqlStatement.localCase3_Transaction2("PHL"));
						break;
				}
			}else{
				//global queries
				switch(statementComboBox.getSelectionModel().getSelectedIndex()){
					case 0:	changePreviewTextArea("Transaction 1: \n" + 
							MySqlStatement.globalCase1_Transaction1() + 
							"\nTransaction 2: \n" +
							MySqlStatement.globalCase1_Transaction2());
						break;
					case 1: changePreviewTextArea("Transaction 1: \n" + 
							MySqlStatement.globalCase2_Transaction1() + 
							"\nTransaction 2: \n" +
							MySqlStatement.globalCase2_Transaction2());
						break;
					case 2: changePreviewTextArea("Transaction 1: \n" + 
							MySqlStatement.globalCase3_Transaction1() + 
							"\nTransaction 2: \n" +
							MySqlStatement.globalCase3_Transaction2());
						break;
				}
			}
		});

		mainButton.setOnAction(e -> {
			doFuckingLocalShit(Node.BOTH_NODE_NUMBER, MySqlStatement.getAll);
		});
		
		replicaButton.setOnAction(e -> {
			doFuckingLocalShit(Node.ASIA_AFRICA_NODE_NUMBER, MySqlStatement.getAll);
		});
		
		simulateButton.setOnAction(e -> {
			if(concurrencyTypeComboBox.getSelectionModel().getSelectedItem().equals(LOCAL)){
				//local queries
				switch(statementComboBox.getSelectionModel().getSelectedIndex()){
				case 0:	Transaction transaction = new Transaction(this, node, node.BOTH_NODE_NUMBER, MySqlStatement.localCase1_Transaction1(), MySqlStatement.localCase1_Transaction2());
						transaction.start();
					break;
				case 1: Transaction transaction1 = new Transaction(this, node, node.BOTH_NODE_NUMBER, MySqlStatement.localCase2_Transaction1(), MySqlStatement.localCase2_Transaction2("AFG"));
						transaction1.start();
					break;
				case 2: Transaction transaction2 = new Transaction(this, node, node.BOTH_NODE_NUMBER, MySqlStatement.localCase3_Transaction1("PHL"), MySqlStatement.localCase3_Transaction2("PHL"));
						transaction2.start();
					break;
				}
			}else{
				//global queries
				switch(statementComboBox.getSelectionModel().getSelectedIndex()){
					case 0:	Transaction transaction = new Transaction(this, node, node.BOTH_NODE_NUMBER, MySqlStatement.globalCase1_Transaction1(), MySqlStatement.globalCase1_Transaction2());
							transaction.start();
						break;
					case 1: Transaction transaction1 = new Transaction(this, node, node.BOTH_NODE_NUMBER, MySqlStatement.globalCase2_Transaction1(), MySqlStatement.globalCase2_Transaction2());
							transaction1.start();
						break;
					case 2: Transaction transaction2 = new Transaction(this, node, node.BOTH_NODE_NUMBER, MySqlStatement.globalCase3_Transaction1(), MySqlStatement.globalCase3_Transaction2());
							transaction2.start();
						break;
				}
			}
	
		});
		
		clearLogButton.setOnAction(e -> {
			logTextArea.setText("");
		});
	}
	
	public void doFuckingLocalShit(int target, String query){
		ArrayList<Integer> nodesChecked = new ArrayList<>();
		ArrayList<String> queries = new ArrayList<String>();
		nodesChecked.add(target);
		queries.add(query);
		ArrayList<String[]> result = node.retrieveData(nodesChecked, queries);
		
		if (!resultsTable.getTable().getColumns ().isEmpty ()){
			for(int i = 0; i < col.size(); i++){
				resultsTable.getTable().getColumns ().removeAll (col.get(i));
			}
			col.clear();
		}
			
		
		if (!resultsTable.getTable().getItems ().isEmpty ()){
			resultsTable.getTable().getItems ().removeAll (row);
			data.clear();
		}
		
			addColumn("CountryCode", 0);
			addColumn("SeriesCode", 1);
			addColumn("YearC", 2);
			addColumn("Data", 3);
			
			for(int i = 0; i < result.size(); i++) {
				row = FXCollections.observableArrayList(result.get(i));
				data.add(row);
			}
			resultsTable.getTable().setItems (data);
	}
	
	public void addColumn(String column, int index) {
		TableColumn c = new TableColumn (column);
		c.setCellValueFactory (new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>> () {
			public ObservableValue<String> call (CellDataFeatures<ObservableList, String> param) {
					return new SimpleStringProperty (param.getValue ().get (index).toString ());
			}
		});
		
		col.add(c);
		resultsTable.getTable().getColumns ().addAll (c);
	}

	@Override
	protected void setLabels() {
		nodeLabel.setText("Node: " + mainRepo);
		replicaLabel.setText("Replica: " + replicaRepo);
	}

}
