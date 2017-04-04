package model;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;



public class Subscribe {
	
	private MulticastSocket mS;
	
	private InetAddress group;

	private int port;

	private PeriodicHello periodicHello;
	
	private String login;
	
	public Subscribe(String login){
		this.port = 5002;		
		this.subscribeMulticast();
		this.login = login;
		this.periodicHello = new PeriodicHello(this.mS,this.group,this.port,this.login);
	}

	private void subscribeMulticast(){
		try {
			group = InetAddress.getByName("225.1.2.3");
			this.mS = new MulticastSocket(this.port);
			this.mS.joinGroup(group);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public MulticastSocket getmS() {
		return mS;
	}

	public PeriodicHello getPeriodicHello(){
		return this.periodicHello;
	}
	
	
}
