package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class CommunicationSocket extends Thread{

	private Socket socket;
	
	private InetAddress remoteIpAddress;
	
	private BufferedReader buffRead;
	
	private BufferedWriter buffWrite;
	
	private ObjectRead objRead;
	
	public CommunicationSocket(Socket socket){
		this.socket = socket;
		this.remoteIpAddress = socket.getInetAddress();
		try {
			InputStreamReader input = new InputStreamReader(socket.getInputStream());
			OutputStreamWriter output = new OutputStreamWriter(socket.getOutputStream());
			buffRead = new BufferedReader(input);
			buffWrite = new BufferedWriter(output);
			objRead = new ObjectRead();
			this.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run(){
		try {
			while(true){
				objRead.setText(buffRead.readLine());
				System.out.println("lecture effectuée");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public InetAddress getRemoteIpAddress(){
		return remoteIpAddress;
	}
	
	public BufferedWriter getBuffWrite(){
		return buffWrite;
	}
	
	public Socket getSocket(){
		return this.socket;
	}
	
	public ObjectRead getObjRead(){
		return this.objRead;
	}
}

