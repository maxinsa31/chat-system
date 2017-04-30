package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

import model.Clients;
import model.CommunicationClient;
import model.CommunicationServer;

public class ClientsTest {

	@Test
	public void testSocketClientExists() {
		Clients clients = new Clients();
		try {
			CommunicationServer cS = new CommunicationServer(50643);
			CommunicationClient cC = new CommunicationClient(InetAddress.getLocalHost(), 50643);
			clients.addClient(cC);

			assertTrue("ne trouve pas un element existant", clients.socketClientExists(InetAddress.getLocalHost()) == true);

			assertTrue("trouve un element inexistant", clients.socketClientExists(InetAddress.getByName("192.168.1.9")) == false);
			
			clients.removeClient(cC);
			
			assertTrue("trouve un element qui vient d'etre supprime", clients.socketClientExists(InetAddress.getLocalHost()) == false);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testFindCommunicationSocket(){
		Clients clients = new Clients();
		try {
			CommunicationServer cS = new CommunicationServer(50644);
			CommunicationClient cC = new CommunicationClient(InetAddress.getLocalHost(), 50644);
			clients.addClient(cC);

			assertTrue("ne trouve pas un element existant", clients.findCommunicationSocket(InetAddress.getLocalHost()).equals(cC));

			assertTrue("trouve un element inexistant", clients.findCommunicationSocket(InetAddress.getByName("192.168.1.9")) == null);
			
			clients.removeClient(cC);
			
			assertTrue("trouve un element qui vient d'etre supprime", clients.findCommunicationSocket(InetAddress.getLocalHost()) == null);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
