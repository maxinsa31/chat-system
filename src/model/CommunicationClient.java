package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class CommunicationClient extends CommunicationSocket{

	//private Socket socket;
	
	
	
	/*private InputStreamReader inputStream;
	
	private OutputStreamWriter outputStream;
	
	private BufferedReader bufRead;
	
	private BufferedWriter bufWrite;*/
	
	public CommunicationClient(InetAddress address, int port) throws IOException{
		super(new Socket(address,port));
	}

}
