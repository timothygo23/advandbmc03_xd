package util;

import java.util.concurrent.Semaphore;

import javafx.scene.control.TextArea;
import network.Node;
import view.NodeView;

public class Transaction {
	private Semaphore semaphore;
	private Thread transaction1;
	private Thread transaction2;
	
	public Transaction(NodeView view, Node node, int target, String tran1, String tran2) {
		semaphore = new Semaphore(1);
		
		transaction1 = new Thread() {
			public void run() {
				try {
					semaphore.acquire(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				view.appendLogText("Transaction 1 is executing.");
				node.executeQueryAt(target, tran1, "PHL");
				semaphore.release();
			}
		};
		
		transaction2 = new Thread() {
			public void run() {
				try {
					semaphore.acquire(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				view.appendLogText("Transaction 2 is executing.");
				node.executeQueryAt(target, tran2, "PHL");
				semaphore.release();
			}
		};
	}
	
	public void start() {
		transaction1.start();
		transaction2.start();
	}
}
