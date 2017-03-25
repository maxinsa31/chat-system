package network;

import java.awt.TrayIcon.MessageType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import user.MessageUser;

import com.sun.xml.internal.ws.client.SenderException;


public class Subscribe {
	
	private MulticastSocket mS;
	 
	private InetAddress group;

	private int port;
	
	public Subscribe(){
		//creerNotreMessage(adresse Ip...)
		//abonnerMulticast
		//balancerLesMessages
		//se mettre à l'écoute via thread
		this.port = 5002;		
		this.subscribeMulticast();
		new PeriodicHello(this.mS,this.group,this.port);
	}

	private void subscribeMulticast(){
		try {
			group = InetAddress.getByName("225.1.2.3");
			this.mS = new MulticastSocket(this.port);
			this.mS.joinGroup(group);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MulticastSocket getmS() {
		return mS;
	}

	
	
}
