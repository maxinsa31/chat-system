package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class CommunicationClient {

	private Socket socket;
	
	
	
	/*private InputStreamReader inputStream;
	
	private OutputStreamWriter outputStream;
	
	private BufferedReader bufRead;
	
	private BufferedWriter bufWrite;*/
	
	public CommunicationClient(InetAddress address, int port){
		try {
			this.socket = new Socket(address,port);
			/*this.inputStream = new InputStreamReader(this.socket.getInputStream());
			this.outputStream = new OutputStreamWriter(this.socket.getOutputStream());
			this.bufRead = new BufferedReader(inputStream);
			this.bufWrite = new BufferedWriter(outputStream);*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Socket getSocket(){
		return this.socket;
	}
	
/*
	public BufferedWriter getBufWrite() {
		return bufWrite;
	}
*/
	
}
