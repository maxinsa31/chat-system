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
	
	private boolean execute;
	
	public CommunicationSocket(Socket socket){
		this.socket = socket;
		this.execute = true;
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
			while(execute){
				String text = buffRead.readLine();
				if(text == null){ /* fermeture de connexion du remote host */
					System.out.println("(CommunicationSocket) deconnexion detectee");
					execute = false;
				}
				else{
					objRead.setText(text);
					System.out.println("lecture effectuée");
				}
			}
			socket.close();
		} catch (IOException e) {
		}
		System.out.println("(CommunicationSocket) remoteIP="+remoteIpAddress+" socket ferme !");
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
	
	public void cancelCommunicationSocket(){
		this.execute = false;
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

