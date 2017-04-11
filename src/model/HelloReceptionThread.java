package model;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.ArrayList;

import ihm.Observer;
import user.MessageUser;


public class HelloReceptionThread extends Thread implements Observable {
	
	private MulticastSocket mS;
	
	private MessageUser msgUser;
	
	private ArrayList<String> list;
	
	private boolean execute;
	
	private Observer obs;
	
	private String login;
	
	public HelloReceptionThread(MulticastSocket mS, String login){
		this.list = new ArrayList<String>();
		this.mS = mS;
		this.execute = true;
		this.login = login;
		this.start();
	}
	
	private int rankUser(MessageUser m){ /* retourne -1 sur le pseudo n'existe pas dans la liste */
		return list.indexOf(m.getPseudo());
	}
	
	public void run(){
		while(execute){
			
			receiveMyObject();
			
			int rank = rankUser(msgUser);
			
			if(msgUser.getEtat() == MessageUser.typeConnect.CONNECTED && rank == -1 && !msgUser.getPseudo().equals(this.login)){
				
				this.list.add(msgUser.getPseudo());
				notifyObservers(msgUser.getPseudo(), rank);
				
			} else if (msgUser.getEtat() == MessageUser.typeConnect.DECONNECTED && rank != -1){
				
					this.list.remove(rank);
					notifyObservers(msgUser.getPseudo(), rank);
			}
		}
	}
	
	public void receiveMyObject(){
		byte [] receiveBuf = new byte[5000];
		DatagramPacket packet = new DatagramPacket(receiveBuf, receiveBuf.length);
		try {
			mS.receive(packet);
			ByteArrayInputStream byteStream = new ByteArrayInputStream(receiveBuf);
			ObjectInputStream oIS = new ObjectInputStream(new BufferedInputStream(byteStream));
			this.msgUser = (MessageUser) oIS.readObject();
			oIS.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void setExecute(boolean execute){
		this.execute = execute;
	}
	
	public void cancelHelloReceptionThread(){
		this.setExecute(false);
	}

	public void addObserver(Observer obs) {
		this.obs = obs;
	}

	public void notifyObservers(String name, int rank) {
		obs.update(name, rank);
	}
}
