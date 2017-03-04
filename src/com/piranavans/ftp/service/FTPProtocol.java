package com.piranavans.ftp.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * This class handle normal FTP connection. This class uses the Apache Common net library
 * 
 * @author PiranavanS
 *
 */
public class FTPProtocol implements Protocol {
	private final ConnectionInfo connectionInfo_;			///> Information needed to make the connection to server
	private final FTPClient ftpClient_ = new FTPClient();	///> client to be used for communication
	
	/**
	 * Constructor
	 * 
	 * @param connectionInfo - connection info need to make the connection
	 */
	public FTPProtocol(ConnectionInfo connectionInfo){
		this.connectionInfo_ = connectionInfo;
	}
	
	/**
	 * Initial method to make the connection to the server
	 */
	@Override
	public void connect() {
		try{
			ftpClient_.connect(connectionInfo_.getHost(), connectionInfo_.getPort());
	           int replyCode = ftpClient_.getReplyCode();
	            if (!FTPReply.isPositiveCompletion(replyCode)) { //check the code to see if connection was good/bad
	                System.out.println("Connect failed");
	                //TODO here i will throw a custom exception RMSException
	                return;
	            }
	 
	            boolean success = ftpClient_.login(connectionInfo_.getUsername(), connectionInfo_.getPassword());
	            if (!success) {
	                System.out.println("Could not login to the server");
	                //TODO here i will throw a custom exception RMSException
	                return;
	            }
			
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
			ftpClient_.logout();
			ftpClient_.disconnect();
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
		try {
			boolean success = ftpClient_.changeWorkingDirectory(path);
            if (!success) {
                System.out.println("Unable to change directory");
                //TODO here i will throw a custom exception RMSException
                return;
            }
		} catch (IOException e) {
			System.out.println("oh oh: " + e.getMessage());
		}

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
			listOfFile = ftpClient_.listFiles();
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
