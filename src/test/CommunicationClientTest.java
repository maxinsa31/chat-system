package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

import message.Message;
import model.CommunicationClient;

public class CommunicationClientTest {

	@Test
	public void testSendAndReceive() {
		ServeurPerroquet perroquet = new ServeurPerroquet(50645);
		try {
			CommunicationClient commClient = new CommunicationClient(InetAddress.getLocalHost(), 50645);
			Message message = new Message();
			message.setData("data");
			commClient.getoS().writeObject(message);
			commClient.getoS().flush();
			Thread.sleep(2000l);
			
			assertTrue("mauvais message recu", commClient.getObjRead().getText().equals("ok data"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
