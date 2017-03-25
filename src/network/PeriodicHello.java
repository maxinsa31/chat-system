package network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;

import user.MessageUser;

public class PeriodicHello extends Thread {
	
	private MulticastSocket mS;
 
	private ObjectOutputStream ooStream;

	private ByteArrayOutputStream baoStream;	

	private MessageUser hello;
	
	private InetAddress group;
	
	private int port;
	
	public PeriodicHello(MulticastSocket mS,InetAddress group, int port){
		this.mS = mS;
		this.port = port;
		this.group = group;
		try {
			this.hello = new MessageUser("MaxX0u_du_31", InetAddress.getLocalHost(), port, MessageUser.typeConnect.CONNECTED);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.baoStream = new ByteArrayOutputStream();
		try {
			this.ooStream = new ObjectOutputStream(baoStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.start();
	}
	
	
	public void run(){
		while(true){
			try {
				this.ooStream.writeObject(this.hello);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DatagramPacket dPacket = new DatagramPacket(this.baoStream.toByteArray(), this.baoStream.toByteArray().length, this.group, this.port);
			try {
				mS.send(dPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(2000l);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
