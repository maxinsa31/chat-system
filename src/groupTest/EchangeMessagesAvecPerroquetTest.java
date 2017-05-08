package groupTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

import message.Message;
import model.CommunicationClient;
import test.ServeurPerroquet;

public class EchangeMessagesAvecPerroquetTest implements FacadeTestPerroquet{

	@Test
	public void testEnvoiPuisReceptionMessage() {
		ServeurPerroquetGroupe server = new ServeurPerroquetGroupe(50645);
		try {
			Object o = openConnexionWithPerroquet(InetAddress.getLocalHost(), 50645);
			sendMessageCoucouToPerroquet(o);
			
			Thread.sleep(1000l);
			
			String messageRecu = receiveMessageFromPerroquet(o);
			
			assertTrue("mauvais message recu", messageRecu.equals("coucou ack"));
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	/* TODO : vous avez à modifier le contenu de cette methode implementee de FacadeTestPerroquet */
	public Object openConnexionWithPerroquet(InetAddress ip, int port) {
		try {
			CommunicationClient commClient = new CommunicationClient(InetAddress.getLocalHost(), 50645);
			return commClient;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/* TODO : vous avez à modifier le contenu de cette methode implementee de FacadeTestPerroquet */
	public void sendMessageCoucouToPerroquet(Object o) {
		Message message = new Message();
		message.setData("coucou");
		try {
			((CommunicationClient)o).getoS().writeObject(message);
			((CommunicationClient)o).getoS().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	/* TODO : vous avez à modifier le contenu de cette methode implementee de FacadeTestPerroquet */
	public String receiveMessageFromPerroquet(Object o) {
		System.out.println("Client recoit message :"+((CommunicationClient)o).getObjRead().getText());
		return ((CommunicationClient)o).getObjRead().getText();
	}

	
	/* methode non utilisee ici */
	public void receiveMessageFromClientAndAnswerToIt(Socket socket) {
	}

}
