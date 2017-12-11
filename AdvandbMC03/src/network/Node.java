package network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import network.Protocols;
import network.Config;
import network.TcpSender;
import network.TcpListener;
import util.Log;
import constants.MySqlStatement;

public class Node {
	public static final int commonPort = 3001;

	   public static final String IP_EUROPE_AMERICA = "192.168.0.27"; //tim
	   public static final String IP_ASIA_AFRICA = "192.168.0.28"; //gab
	   public static final String IP_BOTH = "192.168.0.29"; //luigi

	   public static final int BOTH_NODE_NUMBER = 1;
	   public static final int EUROPE_AMERICA_NODE_NUMBER = 2;
	   public static final int ASIA_AFRICA_NODE_NUMBER = 3;

	   int nodeNumber;

	   //TcpSender myClient;
	   
	   TcpSender allClient = new TcpSender(IP_BOTH, IP_ASIA_AFRICA);
	   TcpSender europeAmericaClient = new TcpSender(IP_EUROPE_AMERICA, IP_BOTH);
	   TcpSender asiaAfricaClient = new TcpSender(IP_ASIA_AFRICA, IP_EUROPE_AMERICA);

	   Connection mainConn; // set auto commit false
	   Connection replicaConn;

	   TcpListener myServer = new TcpListener(this);

	   public Node (int nodeNumber) { // set this different from each other
	      this.nodeNumber = nodeNumber;
	      
	      Properties connectionProps = new Properties();
	      connectionProps.put("user", Config.user);
	      connectionProps.put("password", Config.password);

	      switch(nodeNumber) {
	         case BOTH_NODE_NUMBER:
	        	 
	        	try {
	        		this.mainConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wdi_all_regions", connectionProps);
	        	} catch (SQLException e) {
	        		// TODO Auto-generated catch block
	        		e.printStackTrace();
	        	}
	        	    
	        	try {
	        		this.replicaConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wdi_asia_africa", connectionProps);
	        	} catch (SQLException e) {
	        		// TODO Auto-generated catch block
	        		e.printStackTrace();
	        	}
	            break;
	            
	         case EUROPE_AMERICA_NODE_NUMBER:
	        	 
	        	try {
	        		this.mainConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wdi_europe_and_america", connectionProps);
	     		} catch (SQLException e) {
	     			// TODO Auto-generated catch block
	     			e.printStackTrace();
	     		}
	     	    
	     	    try {
	     	    	this.replicaConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wdi_all_regions", connectionProps);
	     		} catch (SQLException e) {
	     			// TODO Auto-generated catch block
	     			e.printStackTrace();
	     		}
	            break;
	            
	         case ASIA_AFRICA_NODE_NUMBER:
	        	 
	        	try {
	        		this.mainConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wdi_asia_africa", connectionProps);
	      		} catch (SQLException e) {
	      			// TODO Auto-generated catch block
	      			e.printStackTrace();
	      		}
	      	    
	      	    try {
	      	    	this.replicaConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wdi_europe_and_america", connectionProps);
	      		} catch (SQLException e) {
	      			// TODO Auto-generated catch block
	      			e.printStackTrace();
	      		}
	            break;
	      }
	      
	      Log.getInstance().initializeLogFiles(nodeNumber, "log.txt", "transactions.txt");
	      
	      myServer.start();
	      
	    try {
			this.mainConn.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    try {
			this.replicaConn.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	   }


	   public boolean executeQueryAt(int targetNode, String SQLQuery, String countrycode) { // for inserting, updating, deleting
		  
	      switch (targetNode) {
	      
	         case EUROPE_AMERICA_NODE_NUMBER: // europe
				 return sendData( allClient, europeAmericaClient, europeAmericaClient, asiaAfricaClient, SQLQuery);

	         case ASIA_AFRICA_NODE_NUMBER: // asia
				 return sendData( allClient, asiaAfricaClient, allClient, europeAmericaClient, SQLQuery );

	         case BOTH_NODE_NUMBER:
				 String region = getRegionFromCountryCode(countrycode);

				 if (region.contains("Europe") || region.contains("America"))
					 return sendData( allClient, europeAmericaClient, europeAmericaClient, asiaAfricaClient, SQLQuery);
				 else
					 return sendData( allClient, asiaAfricaClient, allClient, europeAmericaClient, SQLQuery );

	            
	      }

		   return false;
	      
	   }
	   
	   public ArrayList<String[]> retrieveData (ArrayList <Integer> targetNodes, ArrayList <String> queries) {
		   ArrayList<String[]> out = new ArrayList<>();
		   
		   for (int i = 0; i < targetNodes.size(); i++) {
			   if (targetNodes.get(i) == this.nodeNumber) { // if local read
				   PreparedStatement pst;
					try {
						pst = mainConn.prepareStatement(queries.get(i));
						ResultSet rs = pst.executeQuery();
						ResultSetMetaData metadata = rs.getMetaData();
						
						int numberOfColumns = metadata.getColumnCount();
						
						while (rs.next()) {
							System.out.println("Result set is not empty");
							String[] toPlace = new String[numberOfColumns];
							for(int j = 0; j < numberOfColumns; j++){
								toPlace[j] = rs.getObject(j+1) + ""; 
							}
							out.add(toPlace);
						}
						
						return out;
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			   } else {  // for global read
				   String statement = queries.get(i) + Protocols.QUERY_FOR_SELECT;
				   
				   switch (targetNodes.get(i)) {
				   
				   case EUROPE_AMERICA_NODE_NUMBER:
					   out.addAll(europeAmericaClient.requestData(statement));
					   break;
				   case ASIA_AFRICA_NODE_NUMBER:
					   out.addAll(asiaAfricaClient.requestData(statement));
					   break;
				   case BOTH_NODE_NUMBER:
					   out.addAll(allClient.requestData(statement));
					   break;
					   
				   }
			   }
		   }
		   
		   return out;
		   
	   }

	   public boolean sendData(TcpSender clientForMain1, TcpSender clientForMain2,
			   TcpSender clientForRep1, TcpSender clientForRep2, String SQLQuery) {

		   String queryForMain = SQLQuery + Protocols.QUERY_FOR_MAIN;
		   String queryForReplica = SQLQuery + Protocols.QUERY_FOR_REPLICA;
		   
		   	String reply1 = clientForMain1.forwardData(queryForMain);
			String reply2 = clientForMain2.forwardData(queryForMain);
			String reply3 = clientForRep1.forwardData(queryForReplica);
			String reply4 = clientForRep2.forwardData(queryForReplica);
			
			System.out.println("Reply1: " + reply1 + " Reply2: " + reply2 + " Reply3: " + reply3 + " Reply4: " + reply4);
			
			if (reply1.contains(Protocols.READY) && reply2.contains(Protocols.READY)
					&& reply3.contains(Protocols.READY) && reply4.contains(Protocols.READY)) {
				clientForMain1.forwardData(Protocols.COMMIT_FOR_MAIN);
				clientForMain2.forwardData(Protocols.COMMIT_FOR_MAIN);
				clientForRep1.forwardData(Protocols.COMMIT_FOR_REPLICA);
				clientForRep2.forwardData(Protocols.COMMIT_FOR_REPLICA);
				return true;
			}
			else {
				clientForMain1.forwardData(Protocols.ABORT);
				clientForMain2.forwardData(Protocols.ABORT);
				clientForRep1.forwardData(Protocols.ABORT);
				clientForRep2.forwardData(Protocols.ABORT);
			}
			
			return false;
		   
	   }
	   
	   public Connection getMainConnection() {
		   return mainConn;
	   }

	   public Connection getReplicaConnection() {
		   return replicaConn;
	   }
	   
	   public void startServerThread() {
	      this.myServer.start();
	   }
	   
	   public String getRegionFromCountryCode (String countrycode) {
		   String region = "";
		   String sql = MySqlStatement.getRegionOfCountryCode + countrycode + "'";
		   
		   try {
			PreparedStatement pstmt = getMainConnection().prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next())
				region = rs.getString(1);
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			return region;
	   }

}
