package model;

import java.net.InetAddress;

public class Receiver {

	private InetAddress IP;
	
	private  String pseudo;
	
	public Receiver(InetAddress IP, String pseudo){
		this.IP = IP;
		this.pseudo = pseudo;
	}

	public InetAddress getIP() {
		return IP;
	}

	public String getPseudo() {
		return pseudo;
	}

	
}
