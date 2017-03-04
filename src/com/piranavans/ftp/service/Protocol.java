package com.piranavans.ftp.service;

import java.io.File;
/**
 * This interface define the methods needed to be implemented by all the protocols. The current implemented 
 * protocol will use 2 open source libraries. One is Jsch which will support the SFTP 
 * while Apache commons net will support various protocols:
 * <ul>
 *	<li>FTP/FTPS</li> 
 * 	<li>FTP over HTTP (experimental)</li>
 * 	<li>NNTP</li>
 * 	<li>SMTP(S)</li>
 *  <li>IMAP(S)</li>
 *  <li>Telnet</li>
 *  <li>TFTP</li>
 *  <li>Finger</li>
 *  <li>Whois</li>
 *  <li>rexec/rcmd/rlogin</li>
 *  <li>Time (rdate) and Daytime</li>
 *  <li>Echo</li>
 *  <li>Discard</li>
 *  <li>NTP/SNTP</li>
 * </ul>
 * @author PiranavanS
 *
 */
public interface Protocol {
	
	/**
	 * Initial method to make the connection to the server
	 */
	public void connect();
	
	/**
	 * Disconnection from the server
	 */
	public void disconnect();
	
	/**
	 * Goto specific path of the remote server
	 * 
	 * @param path - path to goto
	 */
	public void goToDirectory(String path);
	
	/**
	 * Transfer file from local to remote
	 * @param localFile - local file to transfer
	 * 
	 * @param remotePath - remote location to transfer too
	 */
	public void sendFileToRemote(File localFile, String remotePath);
	
	/**
	 * Copy a file from remote to local directory
	 * 
	 * @param fileName - name of the file to be copied
	 * @param localPath - where the file should be copied too
	 */
	public void copyFromRemote(String fileName, String localPath);
	
	/**
	 * Check if the remote location contain this file
	 * 
	 * @param FileName - name of the file to look for
	 * @return true if the file is there, false otherwise
	 */
	public boolean isFileThere(String FileName);
}
