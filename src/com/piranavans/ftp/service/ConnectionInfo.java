package com.piranavans.ftp.service;

/**
 * Class to keep all the connection information
 * 
 * @author PiranavanS
 *
 */
public class ConnectionInfo {
	 private final int port_;  ///> port number for the connection
	 private final String host_;  ///> host to connect
	 private final String password_;  ///> password for the connection
	 private final String username_; ///> username for the connection
	
	 /**
	  * Constructor to initialize the connection object
	  * 
	  * @param host - host of the server
	  * @param port - port to connect
	  * @param password - password used to connect
	  * @param username - username used to connect
	  */
	public ConnectionInfo(String host, int port, String password, String username){
	    this.host_ = host; 
	    this.port_ = port; 
	    this.username_ = username; 
	    this.password_ = password; 
	}
	
	public int getPort(){
		return port_;
	}
	
	public String getHost(){
		return host_;
	}
	
	public String getPassword(){
		return password_;
	}
	
	public String getUsername(){
		return username_;
	}
}
