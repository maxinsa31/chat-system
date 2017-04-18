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
	
	/*private BufferedReader buffRead;
	
	private BufferedWriter buffWrite;*/
	
	private ObjectInputStream iS;
	
	private ObjectOutputStream oS;
	
	private ObjectRead objRead;
	
	private boolean execute;
	
	public CommunicationSocket(Socket socket, boolean isClient){
		this.socket = socket;
		this.execute = true;
		this.remoteIpAddress = socket.getInetAddress();
		try {
			/*InputStreamReader input = new InputStreamReader(socket.getInputStream());
			OutputStreamWriter output = new OutputStreamWriter(socket.getOutputStream());
			buffRead = new BufferedReader(input);
			buffWrite = new BufferedWriter(output);*/
			if(isClient){
				System.out.println("avant getInputStream");
				iS = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
				System.out.println("avant getOutputStream");
				oS = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				System.out.println("apres getOutputStream");
				oS.flush();
			}else{
				System.out.println("avant getOutputStream");
				oS = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				oS.flush();
				System.out.println("apres getOutputStream");
				System.out.println("avant getInputStream");
				iS = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
				System.out.println("apres getInputStream");
			}
			
			objRead = new ObjectRead();
			this.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("fin du communicationSocket()");
	}
	
	public void run(){
		try {
			while(execute){
				//String text = buffRead.readLine();
				Message message;
				try {
					System.out.println("avant lecture");
					message = (Message) iS.readObject();
					System.out.println("un objet a ete lu !!!");

					if(message == null){ /* fermeture de connexion du remote host */
						System.out.println("(CommunicationSocket) deconnexion detectee");
						execute = false;
					}
					else{
						objRead.setText(message.getData());
						System.out.println("lecture effectuée");
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
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
	
	public Socket getSocket(){
		return this.socket;
	}
	
	public ObjectOutputStream getoS(){
		return oS;
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

