package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import org.junit.Test;

import model.HelloReceptionThread;
import model.PeriodicHello;
import model.Receiver;
import model.Subscribe;
import user.MessageUser;

public class HelloReceptionThreadTest {

	@Test
	public void testGetIpAddressOf() {
		try {
			MulticastSocket mS = new MulticastSocket(5002);
			mS.joinGroup(InetAddress.getByName("225.1.2.10"));
			HelloReceptionThread hRT = new HelloReceptionThread(mS, "test");
			
			assertTrue("trouve une adresse IP dans une liste vide", hRT.getIpAddressOf("4rt") == null);
			
			Receiver receiver = new Receiver(InetAddress.getByName("192.168.1.2"), "a", 6452);
			hRT.getList().add(receiver);
			
			assertTrue("ne trouve pas l'addresse IP d'un utilisateur existant", hRT.getIpAddressOf("a").equals(InetAddress.getByName("192.168.1.2")));
			
			assertTrue("trouve une adresse IP d'un utilisateur non existant", hRT.getIpAddressOf("b") == null);
			
			hRT.getList().remove(receiver);
			
			assertTrue("trouve l'addresse IP d'un utilisateur pre-existant mais supprime", hRT.getIpAddressOf("a") == null);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void testGetPortOf() {
		try {
			MulticastSocket mS = new MulticastSocket(5002);
			mS.joinGroup(InetAddress.getByName("225.1.2.10"));
			HelloReceptionThread hRT = new HelloReceptionThread(mS, "test");
			
			assertTrue("trouve un numero de port dans une liste vide", hRT.getPortOf("4rt") == -1);
			
			Receiver receiver = new Receiver(InetAddress.getByName("192.168.1.2"), "a", 6452);
			hRT.getList().add(receiver);
			
			assertTrue("ne trouve pas le numero de port d'un utilisateur existant", hRT.getPortOf("a") == 6452);
			
			assertTrue("trouve un numero de port d'un utilisateur non existant", hRT.getPortOf("b") == -1);
			
			hRT.getList().remove(receiver);
			
			assertTrue("trouve le numero de port d'un utilisateur pre-existant mais supprime", hRT.getPortOf("a") == -1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetPseudoOf() {
		try {
			MulticastSocket mS = new MulticastSocket(5002);
			mS.joinGroup(InetAddress.getByName("225.1.2.10"));
			HelloReceptionThread hRT = new HelloReceptionThread(mS, "test");
			
			assertTrue("trouve un pseudo dans une liste vide", hRT.getPseudoOf(InetAddress.getByName("192.168.1.2")) == null);
			
			Receiver receiver = new Receiver(InetAddress.getByName("192.168.1.2"), "a", 6452);
			hRT.getList().add(receiver);
			
			assertTrue("ne trouve pas le pseudo d'un utilisateur existant", hRT.getPseudoOf(InetAddress.getByName("192.168.1.2")).equals("a"));
			
			assertTrue("trouve un pseudo d'un utilisateur non existant", hRT.getPseudoOf(InetAddress.getByName("192.168.1.1")) == null);
			
			hRT.getList().remove(receiver);
			
			assertTrue("trouve le pseudo d'un utilisateur pre-existant mais supprime", hRT.getPseudoOf(InetAddress.getByName("192.168.1.2")) == null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReceptionHello(){
		Subscribe subscribe;
		try {
			subscribe = new Subscribe("sender",InetAddress.getLocalHost(),50644,5002);

			subscribe.getPeriodicHello().start();
			HelloReceptionThread hrt = new HelloReceptionThread(subscribe.getmS(), "receiver");
			
			hrt.receiveMyObject();
			
			assertTrue("le message recu n'est pas un hello du sender", hrt.getMsgUser().getPseudo().equals("sender") && hrt.getMsgUser().getEtat().equals(MessageUser.typeConnect.CONNECTED));
			
			subscribe.getPeriodicHello().cancelPeriodicHello();
			while(hrt.getMsgUser().getEtat().equals(MessageUser.typeConnect.CONNECTED)){
				hrt.receiveMyObject();
			}
			
			assertTrue("le message recu n'est pas un goodbye", hrt.getMsgUser().getPseudo().equals("sender") && hrt.getMsgUser().getEtat().equals(MessageUser.typeConnect.DECONNECTED));
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
