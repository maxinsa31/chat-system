package model;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

import observer.Observable;
import observer.Observer;
import user.MessageUser;


public class HelloReceptionThread extends Thread implements Observable, Observer {
	
	private MulticastSocket mS;
	
	private MessageUser msgUser;
	
	private ArrayList<Receiver> list;
	
	private Timers timers;
	
	private boolean execute;
	
	private Observer obs;
	
	private String login;
	
	public HelloReceptionThread(MulticastSocket mS, String login){
		this.list = new ArrayList<Receiver>();
		this.timers = new Timers();
		this.mS = mS;
		this.execute = true;
		this.login = login;
		this.start();
	}
	

	private int rankUser(String pseudo){ /* retourne -1 si le pseudo n'existe pas dans la liste */
		int rank = 0;
		for(Receiver R : this.list){
			if(R.getPseudo().equals(pseudo)){
				return rank;
			}else{
				rank ++;
			}
		}
		return -1;
	}
	
	private int rankUser(InetAddress ipAddress){ /* retourne -1 si l'@IP n'existe pas dans la liste */
		int rank = 0;
		for(Receiver R : this.list){
			if(R.getIP().equals(ipAddress)){
				return rank;
			}else{
				rank ++;
			}
		}
		return -1;
	}
	
	public void run(){
		while(execute){
			receiveMyObject();
			
			String pseudo = msgUser.getPseudo();
			int rank = rankUser(pseudo);
			
			if(msgUser.getEtat() == MessageUser.typeConnect.CONNECTED && !pseudo.equals(this.login)){
				if(rank == -1){

					System.out.println("FIRST HELLO : user="+pseudo+" etat="+msgUser.getEtat()+" rank="+rank);
					this.list.add(new Receiver(msgUser.getIP(),pseudo, msgUser.getPort()));
					PseudoToRank p2R = new PseudoToRank(pseudo,rank);
					notifyObservers(p2R);
					timers.launchTimer(pseudo,this);
					
				} else{
					timers.refreshTimer(pseudo,this);
				}
				
			} else if (msgUser.getEtat() == MessageUser.typeConnect.DECONNECTED && rank != -1){

					System.out.println("GOOD-BYE : user="+pseudo+" etat="+msgUser.getEtat()+" rank="+rank);
					this.list.remove(rank);
					PseudoToRank p2R = new PseudoToRank(pseudo,rank);
					notifyObservers(p2R);
					timers.deleteTimer(pseudo);
			} 
		}
		System.out.println("Fermeture helloReceptionThread");
	}
	
	public InetAddress getIpAddressOf(String pseudo){
		int rank = rankUser(pseudo);
		if(rank != -1){
			return list.get(rank).getIP();
		} else{
			return null;
		}
	}
	
	public int getPortOf(String pseudo){
		int rank = rankUser(pseudo);
		if(rank != -1){
			return list.get(rank).getPort();
		} else{
			return -1;
		}
	}
	
	public String getPseudoOf(InetAddress ipAddress){
		int rank = rankUser(ipAddress);
		if(rank != -1){
			return list.get(rank).getPseudo();
		} else{
			return null;
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

	/* avertir l'ihm d'une deconnexion */
	public void notifyObservers(Object o) {
		obs.update(o);
	}

	public MessageUser getMsgUser() {
		return msgUser;
	}

	/* en reaction a une expiration de timer */
	public void update(Object o) {
		if(o instanceof TaskTimer){
			String pseudo = ((TaskTimer)o).getName();
			int rank = rankUser(pseudo);
			this.list.remove(rank);
			PseudoToRank p2R = new PseudoToRank(pseudo,rank);
			notifyObservers(p2R);
			timers.deleteTimer(pseudo);
		}
	}
	
}
