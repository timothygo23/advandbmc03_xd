package util;

import javafx.scene.control.TextArea;
import network.Node;
import view.NodeView;

public class Transaction {
	private Thread transaction1;
	private Thread transaction2;
	private boolean lock;
	
	public Transaction(NodeView view, Node node, int target, String tran1, String tran2) {
		lock = false;
		transaction1 = new Thread() {
			public void run() {
				while(lock);
				lock = true;
				view.appendLogText("Transaction 1 is executing.");
				node.executeQueryAt(target, tran1, "PHL");
				lock = false;
			}
		};
		
		transaction2 = new Thread() {
			public void run() {
				while(lock);
				lock = true;
				view.appendLogText("Transaction 2 is executing.");
				node.executeQueryAt(target, tran2, "PHL");
				lock = false;
			}
		};
	}
	
	public void start() {
		transaction1.start();
		transaction2.start();
	}
}
