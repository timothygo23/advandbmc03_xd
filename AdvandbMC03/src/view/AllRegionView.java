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
					case 0:	System.out.println("1");
						break;
					case 1: System.out.println("2");
						break;
					case 2: System.out.println("3");
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
			
		});
		
		clearLogButton.setOnAction(e -> {
			
		});
	}
	
	public void doFuckingLocalShit(int target, String query){
		
	}

	@Override
	protected void setLabels() {
		nodeLabel.setText("Node: " + mainRepo);
		replicaLabel.setText("Replica: " + replicaRepo);
	}
	
	public boolean existsInTable(JTable table, Object[] entry) {
	    // Get row and column count
	    int rowCount = table.getRowCount();
	    int colCount = table.getColumnCount();

	    // Get Current Table Entry
	    String curEntry = "";
	    for (Object o : entry) {
	        String e = o.toString();
	        curEntry = curEntry + " " + e;
	    }

	    // Check against all entries
	    for (int i = 0; i < rowCount; i++) {
	        String rowEntry = "";
	        for (int j = 0; j < colCount; j++)
	            rowEntry = rowEntry + " " + table.getValueAt(i, j).toString();
	        if (rowEntry.equalsIgnoreCase(curEntry)) {
	            return true;
	        }
	    }
	    return false;
	}

}
