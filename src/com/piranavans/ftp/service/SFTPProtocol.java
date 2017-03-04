package com.piranavans.ftp.service;

import java.io.File;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * This class handle normal SFTP connection. This class uses the Jsch library
 * 
 * @author PiranavanS
 *
 */
public class SFTPProtocol implements Protocol {
	 private final ConnectionInfo connectionInfo_;	///> Information needed to make the connection to server
	 private final JSch jsch_ = new JSch();			///> Jsch object to create session and channels
	 private Session session_;						///> to initialize session
	 private ChannelSftp channelSftp_;				///> sftp channel to communication with the server
	 
	/**
	 * Constructor
	 * 
	 * @param connectionInfo - connection info need to make the connection
	 */
	public SFTPProtocol(ConnectionInfo connectionInfo){
		this.connectionInfo_ = connectionInfo;
	}
	
	/**
	 * Initial method to make the connection to the server
	 */
	@Override
	public void connect() {
		try{
			//introduce insecurities
			//http://stackoverflow.com/questions/2003419/com-jcraft-jsch-jschexception-unknownhostkey
		    JSch.setConfig("StrictHostKeyChecking", "no"); 
		    
			session_ = jsch_.getSession(connectionInfo_.getUsername(), connectionInfo_.getHost(), connectionInfo_.getPort());
			session_.setPassword(connectionInfo_.getPassword());
			session_.connect();
		    System.out.println("session is created and has been connected...");
		    Channel channel = session_.openChannel("sftp");
		    channel.connect();
		    channelSftp_ = (ChannelSftp) channel;
		} catch(Exception e){
			System.out.println("oh oh: " + e.getMessage());
		}
	
	}
	
	/**
	 * logout and disconnection from the server
	 */
	@Override
	public void disconnect() {
		channelSftp_.exit();
		session_.disconnect();
	}
	
	/**
	 * Goto specific path of the remote server
	 * 
	 * @param path - path to goto
	 */
	@Override
	public void goToDirectory(String path) {
		try {
			channelSftp_.cd(path);
		} catch (SftpException e) {
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
		try {
			channelSftp_.get(fileName, localPath);
		} catch (SftpException e) {
			System.out.println("oh oh: " + e.getMessage());
		}
	
	}
	
	/**
	 * Check if the remote location contain this file
	 * 
	 * @param FileName - name of the file to look for
	 * @return true if the file is there, false otherwise
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean isFileThere(String FileName) {
		try {
			Vector<ChannelSftp.LsEntry> list = channelSftp_.ls(channelSftp_.pwd());
			for(ChannelSftp.LsEntry entry : list) {
			    if(FileName.equals(entry.getFilename())){
			    	return true;
			    }
			}
		} catch (SftpException e) {
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
