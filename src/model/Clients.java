package model;

import java.net.InetAddress;
import java.util.ArrayList;

public class Clients {

	private ArrayList<CommunicationClient> cClients;
	
	public Clients(){
		this.cClients = new ArrayList<CommunicationClient>();
	}
	
	public boolean socketClientExists(InetAddress ip){
		 for (CommunicationClient c : cClients){
			 if(c.getSocket().getInetAddress().equals(ip)){
				 return true;
			 }
		 }
		 return false;
	}
	
	public CommunicationClient findCommunicationSocket(InetAddress ipAddress){
		for (CommunicationClient c : cClients){
			 if(c.getSocket().getInetAddress().equals(ipAddress)){
				 return c;
			 }
		}
		return null;
	}
	
	public void addClient(CommunicationClient e){
		this.cClients.add(e);
	}
	
	public void removeClient(CommunicationClient e){
		this.cClients.remove(e);
	}
	
	public ArrayList<CommunicationClient> getClients(){
		return this.cClients;
	}
	
	public void cancelSockets(){
		for(CommunicationClient cC : cClients){
			cC.cancelCommunicationSocket();
		}
	}
	
	
}
