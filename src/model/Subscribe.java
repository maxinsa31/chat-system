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

	private int myMulticastPort;

	private PeriodicHello periodicHello;
	
	private String login;
	
	private InetAddress myIp;
	
	private int myServerPort;
	
	public Subscribe(String login, InetAddress myIp, int myServerPort, int myMulticastPort){
		this.myMulticastPort = myMulticastPort;		
		this.myServerPort = myServerPort;
		this.myIp = myIp;
		this.subscribeMulticast();
		this.login = login;
		this.periodicHello = new PeriodicHello(this.mS,this.group,this.myServerPort,this.login,this.myIp,this.myMulticastPort);
	}

	private void subscribeMulticast(){
		try {
			group = InetAddress.getByName("225.1.2.3");
			this.mS = new MulticastSocket(this.myMulticastPort);
			try {
				this.mS.setNetworkInterface(NetworkInterface.getByInetAddress(this.myIp));
			} catch (SocketException e1) {
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
