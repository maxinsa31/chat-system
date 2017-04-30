package test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import message.Message;
import model.CommunicationSocket;

public class ServeurPerroquet extends Thread{

	private ServerSocket waitForConnectionSocket;
	
	private int port;
	
	private CommunicationSocket commSocket;
	
	public ServeurPerroquet(int port){
		this.port = port;
		try {
			this.waitForConnectionSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.start();
	}
	
	public void run(){
		
		try {
			Socket communicationSocket = this.waitForConnectionSocket.accept();
			commSocket = new CommunicationSocket(communicationSocket,false);
			
			Message message = (Message) commSocket.getiS().readObject();
			System.out.println("reception message = "+message.getData());
			
			Message messageToSend = new Message();
			messageToSend.setData("ok "+message.getData());
			commSocket.getoS().writeObject(messageToSend);
			commSocket.getoS().flush();
			
			
		} catch (IOException e) {
			System.out.println("(ServeurPerroquet) Fermeture du socket du serveur d'ecoute");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
