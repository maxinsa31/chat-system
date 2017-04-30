package model;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import observer.Observable;
import observer.Observer;

public class CommunicationServer extends Thread implements Observable{

	private ServerSocket waitForConnectionSocket;
	
	private int port;
	
	private ArrayList<CommunicationSocket> socketListNeverOpened;
	
	private ArrayList<CommunicationSocket> socketList;
	
	private boolean execute;
	
	private Observer obs;
	
	public CommunicationServer(int port){
		this.port = port;
		this.socketList = new ArrayList<CommunicationSocket>();
		this.socketListNeverOpened = new ArrayList<CommunicationSocket>();
		this.execute = true;
		try {
			this.waitForConnectionSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.start();
	}
	
	public void run(){
		while(execute){
			try {
				Socket communicationSocket = this.waitForConnectionSocket.accept();
				CommunicationSocket comSocket = new CommunicationSocket(communicationSocket,false);
				comSocket.start();
				socketListNeverOpened.add(comSocket);
				/* notifie au controller qu'une connexion TCP a ete ouverte avec un certain host */
				notifyObservers(comSocket);
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
	
	public boolean socketServerNeverOpenedExists(InetAddress ipAddress){
		for(CommunicationSocket cS : socketListNeverOpened){
			if(cS.getRemoteIpAddress().equals(ipAddress)){
				return true;
			}
		}
		return false;
	}
	
	public CommunicationSocket findCommunicationSocketNeverUsed(InetAddress remoteIpAddress){
		for(CommunicationSocket cS : socketListNeverOpened){
			if(cS.getRemoteIpAddress().equals(remoteIpAddress)){
				return cS;
			}
		}
		return null;
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
		this.cancelSockets();
	}
	
	public void cancelSockets(){
		for(CommunicationSocket cS : socketList){
			cS.cancelCommunicationSocket();
		}
		for(CommunicationSocket cS : socketListNeverOpened){
			cS.cancelCommunicationSocket();
		}
	}
	
	public void transferFromNeverUsedToUsed(CommunicationSocket cS){
		socketList.add(cS);
		socketListNeverOpened.remove(cS);
	}

	public void addObserver(Observer obs) {
		this.obs = obs;		
	}

	public void notifyObservers(Object o) {
		if(obs != null)
			obs.update(o);
	}
	
}
