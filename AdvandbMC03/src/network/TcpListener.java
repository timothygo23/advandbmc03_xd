package network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import network.Protocols;
import network.Node;
import util.Log;

public class TcpListener extends Thread {

	   private Node serversNode;

	   public TcpListener (Node node) {
	      this.serversNode = node;
	   }
	   
	   @Override
	   public void run() {
	       try {
	    	  System.out.println("Server Running xd");
	          ServerSocket welcomeSocket = new ServerSocket(Node.commonPort);
	          String clientSentence;
	          String capitalizedSentence;

	          while(true)
	          {
	             Socket connectionSocket = welcomeSocket.accept();
	             BufferedReader inFromClient =
	                     new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
	             DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				 ObjectOutputStream objectOutput = new ObjectOutputStream(connectionSocket.getOutputStream());
				 
	             clientSentence = inFromClient.readLine();
	             processMessage (clientSentence, outToClient, objectOutput);
	          }
	       } catch (IOException e) {
	          e.printStackTrace();
	       }
	    }
	   
	   public void processMessage (String clientSentence, DataOutputStream outToClient, ObjectOutputStream objectOutput) {
		   if (Protocols.isCommitOrAbort(clientSentence)) {
			   if (clientSentence.contains(Protocols.COMMIT_FOR_MAIN)) {
				   try {
					serversNode.getMainConnection().commit();
					
					Log.getInstance().writeToLog(Log.getInstance().getLastTransactionNumber(), "COMMIT FOR MAIN");
					
					System.out.println("Server: main repository committed.");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   } else if (clientSentence.contains(Protocols.COMMIT_FOR_REPLICA)) {
				   try {
						serversNode.getReplicaConnection().commit();
						
						Log.getInstance().writeToLog(Log.getInstance().getLastTransactionNumber(), "COMMIT FOR REPLICA");

						System.out.println("Server: replica repository committed.");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			   } else if (clientSentence.contains(Protocols.ABORT)) {
				   try {
						serversNode.getMainConnection().rollback();
						
						Log.getInstance().writeToLog(Log.getInstance().getLastTransactionNumber(), "ABORT FOR MAIN");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				   
				   try {
						serversNode.getReplicaConnection().rollback();
						Log.getInstance().writeToLog(Log.getInstance().getLastTransactionNumber(), "ABORT FOR REPLICA");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			   }
			   
			   try {
				outToClient.writeBytes(Protocols.JUST_CONTINUE + '\n');
				System.out.println("CONTINUE LANG BHE");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   
		   } else {
			   if (clientSentence.contains(Protocols.QUERY_FOR_MAIN)) {
				   clientSentence = clientSentence.replaceAll(Protocols.QUERY_FOR_MAIN, "");
				   System.out.println("TANGGAP: " + clientSentence);
				   
				   PreparedStatement prepStatement = null;
				   int num = 0;
				   try {
					prepStatement = serversNode.getMainConnection().prepareStatement(clientSentence);
					
					prepStatement.execute();
					
					num = Log.getInstance().newTransaction(clientSentence);
					Log.getInstance().writeToLog(num, "MAIN START");
					Log.getInstance().writeToLog(num, "MAIN EXECUTE");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				   
				   try {
					   Log.getInstance().writeToLog(num, "MAIN READY");
						outToClient.writeBytes(Protocols.READY + '\n');
						System.out.println("Sent Message: " + Protocols.READY);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				   
			   } else if (clientSentence.contains(Protocols.QUERY_FOR_REPLICA)) {
				   clientSentence = clientSentence.replaceAll(Protocols.QUERY_FOR_REPLICA, "");
				   System.out.println("Received for Replica: " + clientSentence);
				   
				   PreparedStatement prepStatement = null;
				   int num = 0;
				   try {
					prepStatement = serversNode.getReplicaConnection().prepareStatement(clientSentence);
					
					prepStatement.execute();
					num = Log.getInstance().newTransaction(clientSentence);
					Log.getInstance().writeToLog(num, "REPLICA START");
					Log.getInstance().writeToLog(num, "REPLICA EXECUTE");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				   try {
					Log.getInstance().writeToLog(num, "REPLICA READY");
					outToClient.writeBytes(Protocols.READY + '\n');
					System.out.println("Sent Message: " + Protocols.READY);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   } else if (clientSentence.contains(Protocols.QUERY_FOR_SELECT) || clientSentence.contains(Protocols.QUERY_FOR_REPLICA)) {
				   clientSentence = clientSentence.replaceAll(Protocols.QUERY_FOR_SELECT, "");
				   System.out.println("Received for Select: " + clientSentence);
				   
				   try {
						PreparedStatement pstmt = null;
						
						if (clientSentence.contains(Protocols.QUERY_FOR_SELECT))
							pstmt = serversNode.getMainConnection().prepareStatement(clientSentence);
						else
							pstmt = serversNode.getReplicaConnection().prepareStatement(clientSentence);
						
						ResultSet rs = pstmt.executeQuery();
						
						// Write rs to server
						
						
						ArrayList <String[]> result = new ArrayList <String[]> ();
						ResultSetMetaData metadata = rs.getMetaData();
						
						int numberOfColumns = metadata.getColumnCount();
						
						while (rs.next()) {
							String[] toPlace = new String[numberOfColumns];
							for(int j = 0; j < numberOfColumns; j++){
								toPlace[j] = rs.getObject(j+1) + "";
							}
							result.add(toPlace);
					          
						}
						
						//DataTransferObject dto = new DataTransferObject(rs);
						
						try {
							objectOutput.writeObject(result);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			   }
		   }
	   }
}
