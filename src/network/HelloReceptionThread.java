package network;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import user.MessageUser;


public class HelloReceptionThread extends Thread{

	 
	private MulticastSocket mS;
	
	private MessageUser msgUser;
	
	private DefaultListModel<String> list;
	
	public HelloReceptionThread(DefaultListModel<String> list, MulticastSocket mS){
		this.list = list;
		this.mS = mS;
		this.start();
	}
	
	private int rankUser(MessageUser m){
		return list.indexOf(m.getPseudo());
	}
	
	public void run(){
		while(true){
			byte[] rcvBuf = new byte[5000];
			DatagramPacket packet = new DatagramPacket(rcvBuf, rcvBuf.length);
			try {
				mS.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ByteArrayInputStream byteStream = new ByteArrayInputStream(rcvBuf);
			ObjectInputStream oIS;
			try {
				oIS = new ObjectInputStream(new BufferedInputStream(byteStream));
				msgUser = (MessageUser)oIS.readObject();
				
				if(rankUser(msgUser) == -1 && !msgUser.getPseudo().equals("MaxX0u_du_31")){
					this.list.addElement(msgUser.getPseudo());
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}
