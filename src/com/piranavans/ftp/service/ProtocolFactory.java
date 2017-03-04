package com.piranavans.ftp.service;

/**
 * Factory class to return different type of protocols
 * @author PiranavanS
 *
 */
public class ProtocolFactory {
	
	/**
	 * Private constructor
	 */
	private ProtocolFactory(){
		//don't need to be initialized and only contains one static method atm
	}
	
	/**
	 * This method will initialize a Protocol of type depending on the passed parameter
	 * 
	 * @param connectionInfo - information need to connect to server
	 * @param type - type of protocol to create
	 * @return either a SFTPProtocol, FTPProtocol or FTPSProtocol
	 */
	 public static Protocol getProtocol(ConnectionInfo connectionInfo, String type){
		 if(type.equals("SFTP")){
			 return new SFTPProtocol(connectionInfo);
		 } else if(type.equals("FTP")){
			 return new FTPProtocol(connectionInfo);
		 } else if(type.equals("FTPS")){
			 return new FTPSProtocol(connectionInfo);
		 } else{
			 return null;
		 }
	 }
}

