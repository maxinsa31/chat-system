package test;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

import model.HelloReceptionThread;
import model.Subscribe;
import user.MessageUser;

public class PeriodicHelloTest {

	@Test
	public void testSendHelloGoodBye() {
		Subscribe subscribe;
		try {
			subscribe = new Subscribe("sender",InetAddress.getLocalHost(),50644,5002);
			HelloReceptionThread hrt = new HelloReceptionThread(subscribe.getmS(), "receiver");
			hrt.start();
			
			try {
				MessageUser hello = new MessageUser("sender", InetAddress.getLocalHost(), 50644, MessageUser.typeConnect.CONNECTED);
				subscribe.getPeriodicHello().sendMyObject(hello);
				Thread.sleep(1000l);
				
				assertTrue("le message recu n'est pas un hello du sender", hrt.getMsgUser().getPseudo().equals("sender") && hrt.getMsgUser().getEtat().equals(MessageUser.typeConnect.CONNECTED));
				
				MessageUser goodBye = new MessageUser("sender", InetAddress.getLocalHost(), 50644, MessageUser.typeConnect.DECONNECTED);
				subscribe.getPeriodicHello().sendMyObject(goodBye);
				Thread.sleep(1000l);
				
				assertTrue("le message recu n'est pas un goodbye du sender", hrt.getMsgUser().getPseudo().equals("sender") && hrt.getMsgUser().getEtat().equals(MessageUser.typeConnect.DECONNECTED));


			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
	}

}
