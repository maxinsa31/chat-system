import java.net.InetAddress;
import java.net.UnknownHostException;

import controller.Controller;
import ihm.View;

public class launchChatSystem {

	public static void main(String[] args) {
		System.out.println("Bienvenue dans notre chat system");
		
		/* Paramètres importants */
		try {
			InetAddress myIp = InetAddress.getByName("192.168.1.46");
			int myServerPort = 50643;
			int myMulticastPort = 5002;
			
			
			/* Vue */
			View view = new View();
			
			/* Controller */
			Controller controller = new Controller(view,myIp,myServerPort,myMulticastPort);
			
			/* Ajout des actionListener de la vue */
			view.getConnectionWindow().getbConnection().addActionListener(controller);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		

	}

}
