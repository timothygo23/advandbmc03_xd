package view;

import constants.MySqlStatement;
import controller.MainController;
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
			
		});
		
		replicaButton.setOnAction(e -> {
			
		});
		
		simulateButton.setOnAction(e -> {
			
		});
		
		clearLogButton.setOnAction(e -> {
			
		});
	}

	@Override
	protected void setLabels() {
		nodeLabel.setText("Node: " + mainRepo);
		replicaLabel.setText("Replica: " + replicaRepo);
	}

}
