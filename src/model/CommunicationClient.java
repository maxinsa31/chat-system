package model;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class CommunicationClient extends CommunicationSocket{
	
	public CommunicationClient(InetAddress address, int port) throws IOException{
		super(new Socket(address,port),true);
	}

}
