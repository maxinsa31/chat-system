package model;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class CommunicationServer extends Thread {

	private ServerSocket waitForConnectionSocket;
	
	private int port;
	
	private ArrayList<CommunicationSocket> socketList;
	
	private boolean execute;
	
	public CommunicationServer(){
		this.port = 50644;
		this.socketList = new ArrayList<CommunicationSocket>();
		this.execute = true;
		try {
			this.waitForConnectionSocket = new ServerSocket(this.port);
			/*TODO :
			 * - le port 53000 ne marche pas
			 * - penser à fermer le port quand il y a deconnexion sinon ne marche pas quand il y a reconnexion
			 */
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.start();
	}
	
	public void run(){

		while(execute){
			try {
				Socket communicationSocket = this.waitForConnectionSocket.accept();
				socketList.add(new CommunicationSocket(communicationSocket));				
			} catch (IOException e) {
				System.out.println("(CommunicationServer) Fermeture du socket du serveur d'ecoute");
			}
		}
		System.out.println("(CommunicationServer) Fermeture du serveur d'ecoute de demande connexion TCP");
	}
	
	public boolean socketServerExists(InetAddress ipAddress){
		for(CommunicationSocket cS : socketList){
			if(cS.getRemoteIpAddress().equals(ipAddress)){
				return true;
			}
		}
		return false;
	}
	
	public CommunicationSocket findCommunicationSocket(InetAddress remoteIpAddress){
		for(CommunicationSocket cS : socketList){
			if(cS.getRemoteIpAddress().equals(remoteIpAddress)){
				return cS;
			}
		}
		return null;
	}
	
	private void setExecute(boolean execute){
		this.execute = execute;
	}
	
	/* methode appelee lorsqu' il y a deconnexion  */
	public void cancelCommunicationServer(){
		this.setExecute(false);
		try {
			this.waitForConnectionSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
