package groupTest;

import java.net.InetAddress;
import java.net.Socket;

public interface FacadeTestPerroquet {

	/* retourne l'objet avec lequel vous pourrez envoyer et recevoir des messages */
	public Object openConnexionWithPerroquet(InetAddress ip, int port);
	
	/* methode qui envoie le message "coucou" au perroquet */
	public void sendMessageCoucouToPerroquet(Object o);
	
	/* methode qui permet de recevoir un message de la part du perroquet*/
	public String receiveMessageFromPerroquet(Object o);
	
	/* methode qui permet de recevoir un message ("coucou") de la part du client et d'y répondre ("coucou ack") */
	public void receiveMessageFromClientAndAnswerToIt(Socket socket);
	
}
