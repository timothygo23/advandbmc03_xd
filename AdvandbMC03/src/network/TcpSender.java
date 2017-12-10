package network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import network.Protocols;
import network.Node;

public class TcpSender {

	   private String serverAddress;
	   private String replicaAddress;

	   public TcpSender (String IPAddress, String replicaAddress) {
	      this.serverAddress = IPAddress;
	      this.replicaAddress = replicaAddress;
	   }

	   public String forwardData(String input) {

	       String feedback = "";
		   
	      try {
	         String sentence = input;
	         Socket clientSocket = new Socket(this.serverAddress, Node.commonPort);
	         DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
	         BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	         outToServer.writeBytes(sentence + '\n');
	         
	         feedback = inFromServer.readLine();
	         
	         clientSocket.close();

	      } catch (IOException e) {
	    	  e.printStackTrace();
	    	  System.out.println("TIMEOUT XDDDD");
	      }
	      
	      return feedback;

	   }
	   
	   public ArrayList<String[]> requestData(String input) { // strictly for SELECT
		   ArrayList <String[]> result = null;
			try {
				Socket clientSocket = new Socket(serverAddress, Node.commonPort);
				DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
				
				outToServer.writeBytes(input + '\n');
				
				ObjectInputStream objectInput = new ObjectInputStream(clientSocket.getInputStream());
				Object object = objectInput.readObject();
				result = (ArrayList <String[]>) object;
				
			} catch (Exception e) {
				System.out.println("TIMEOUT XDDDD");
				
				input = input.replaceAll(Protocols.QUERY_FOR_SELECT, Protocols.QUERY_FOR_REPLICA);
				
				System.out.println("WHERE'S MY REPLICA AT FAM?");
				
				try {
					Socket clientSocket = new Socket(replicaAddress, Node.commonPort);
					DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
					
					outToServer.writeBytes(input + '\n');
					
					ObjectInputStream objectInput = new ObjectInputStream(clientSocket.getInputStream());
					Object object = objectInput.readObject();
					result = (ArrayList <String[]>) object;
					
				} catch (Exception ex) {
					ex.printStackTrace();
					System.out.println("TIMEOUT XDDDDDD");
				}
				
			}
			return result;
	   }

}
