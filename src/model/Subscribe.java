package model;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
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
		this.periodicHello = new PeriodicHello(this.mS,this.group,50643,this.login);
	}

	private void subscribeMulticast(){
		try {
			group = InetAddress.getByName("225.1.2.3");
			this.mS = new MulticastSocket(this.port);
			try {
				this.mS.setNetworkInterface(NetworkInterface.getByInetAddress(InetAddress.getByName("192.168.1.22")));
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
