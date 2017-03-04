package com.piranavans.ftp.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

/**
 * This class handle FTP connection with SSL. This class uses the Apache Common net library
 * 
 * @author PiranavanS
 *
 */
public class FTPSProtocol implements Protocol {
	private final ConnectionInfo connectionInfo_;	///> Information needed to make the connection to server
	private final FTPSClient ftpsClient_;			///> client to be used for communication

	/**
	 * Constructor - depending on the port number either implicit or explicit connection protocol is created
	 *  
	 * @param connectionInfo - connection info need to make the connection
	 */
	public FTPSProtocol(ConnectionInfo connectionInfo){
		this.connectionInfo_ = connectionInfo;
		if(connectionInfo.getPort() == 990){
			this.ftpsClient_= new FTPSClient(true); //implicit 
		} else {
			this.ftpsClient_= new FTPSClient(); //explicit
		}
	}
	
	/**
	 * Initial method to make the connection to the server
	 */
	@Override
	public void connect() {
		try{
			ftpsClient_.setConnectTimeout(5000);
			ftpsClient_.connect(connectionInfo_.getHost(), connectionInfo_.getPort());
	           int replyCode = ftpsClient_.getReplyCode();
	            if (!FTPReply.isPositiveCompletion(replyCode)) {
	                System.out.println("Connect failed");
	                //TODO here i will throw a custom exception RMSException
	                return;
	            }
	 
	            boolean success = ftpsClient_.login(connectionInfo_.getUsername(), connectionInfo_.getPassword());
	            if (!success) {
	                System.out.println("Could not login to the server");
	                //TODO here i will throw a custom exception RMSException
	                return;
	            }
	            ftpsClient_.enterLocalPassiveMode(); //switches both the client and the server to the passive mode.
	            ftpsClient_.execPBSZ(0); 
	            ftpsClient_.execPROT("P"); // for protected TLS data channel(server requires encrypt data transfer)
		} catch (Exception e){
			System.out.println("oh oh: " + e.getMessage());
		}
	}

	/**
	 * logout and disconnection from the server
	 */
	@Override
	public void disconnect() {
		try {
			ftpsClient_.logout();
			ftpsClient_.disconnect();
		} catch (IOException e) {
			System.out.println("oh oh: " + e.getMessage());
		}
	}
	
	/**
	 * Goto specific path of the remote server
	 * 
	 * @param path - path to goto
	 */
	@Override
	public void goToDirectory(String path) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Copy a file from remote to local directory
	 * 
	 * @param fileName - name of the file to be copied
	 * @param localPath - where the file should be copied too
	 */
	@Override
	public void copyFromRemote(String fileName, String localPath) {
		// TODO Auto-generated method stub

	}

	/**
	 * Check if the remote location contain this file
	 * 
	 * @param FileName - name of the file to look for
	 * @return true if the file is there, false otherwise
	 */
	@Override
	public boolean isFileThere(String fileName) {
		FTPFile[] listOfFile;
		try {
			//get list of all files the loop to find the input file
			listOfFile = ftpsClient_.listFiles();
			for (FTPFile file: listOfFile){
				if (file.getName().equals(fileName)){
					return true;
				}
			}
		} catch (IOException e) {
			System.out.println("oh oh: " + e.getMessage());
		}
		return false;
	}

	/**
	 * Transfer file from local to remote
	 * @param localFile - local file to transfer
	 * 
	 * @param remotePath - remote location to transfer too
	 */
	@Override
	public void sendFileToRemote(File localFile, String remotePath) {
		// TODO Auto-generated method stub
		
	}

}
