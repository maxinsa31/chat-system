package model;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import user.MessageUser;

public class PeriodicHello extends Thread {
	
	private MulticastSocket mS;	

	private MessageUser hello;
	
	private InetAddress group;
	
	private int port;
	
	private boolean execute;
	
	private String login;
	
	public PeriodicHello(MulticastSocket mS,InetAddress group, int port, String login){
		this.mS = mS;
		this.port = port;
		this.group = group;
		this.execute = true;
		this.login = login;
		try {
			try {
				System.out.println("adresse IP = "+InetAddress.getByName("192.168.1.22")+"interface multi ="+this.mS.getNetworkInterface());
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//this.hello = new MessageUser(this.login,InetAddress.getLocalHost(), port, MessageUser.typeConnect.CONNECTED);
			this.hello = new MessageUser(this.login,InetAddress.getByName("192.168.1.22"), port, MessageUser.typeConnect.CONNECTED);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.start();
	}
	
	
	public void run(){
		/* boucle tant que la variable 'execute' n'est pas modifiée par la classe UsersWindow */
		while(execute){
			sendMyObject(hello);
			//System.out.println("Message je suis connecte");
			try {
				Thread.sleep(2000l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		/* thread arrêté car click sur le bouton 'deconnection'
		 * 		-> il faut donc envoyer un message d'indication de déconnexion
		 */
		try {
			MessageUser goodBye = new MessageUser(this.login, InetAddress.getLocalHost(), port, MessageUser.typeConnect.DECONNECTED);
			sendMyObject(goodBye);
			System.out.println("Message deconnexion");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		 
	}
	
	private void sendMyObject(MessageUser m){
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oOS = new ObjectOutputStream(new BufferedOutputStream(byteStream));
			oOS.flush();
			oOS.writeObject(m);
			oOS.flush();
			byte[] sendBuf = byteStream.toByteArray();
			DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length,this.group,5002);
			mS.send(packet);
			oOS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void setExecute(boolean execute){
		this.execute = execute;
	}
	
	public void cancelPeriodicHello(){
		this.setExecute(false);
	}
}
