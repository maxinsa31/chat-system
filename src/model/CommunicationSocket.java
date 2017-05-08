package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import message.Message;

public class CommunicationSocket extends Thread{

	private Socket socket;
	
	private InetAddress remoteIpAddress;
	
	private ObjectInputStream iS;
	
	private ObjectOutputStream oS;
	
	private ObjectRead objRead;
	
	private boolean execute;
	
	public CommunicationSocket(Socket socket, boolean isClient){
		this.socket = socket;
		this.execute = true;
		this.remoteIpAddress = socket.getInetAddress();
		try {
			if(isClient){
				iS = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
				oS = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				oS.flush();
			}else{
				oS = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				oS.flush();
				iS = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			}
			
			objRead = new ObjectRead();
			//this.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		try {
			while(execute){
				Message message;
				try {
					message = (Message) iS.readObject();

					if(message == null){ /* fermeture de connexion du remote host */
						execute = false;
					}
					else{
						objRead.setText(message.getData());
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			socket.close();
		} catch (IOException e) {
		}
	}
	
	public InetAddress getRemoteIpAddress(){
		return remoteIpAddress;
	}
	
	public Socket getSocket(){
		return this.socket;
	}
	
	public ObjectOutputStream getoS(){
		return oS;
	}
	
	public ObjectInputStream getiS(){
		return iS;
	}
	
	public ObjectRead getObjRead(){
		return this.objRead;
	}
	
	public void cancelCommunicationSocket(){
		this.execute = false;
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

