package groupTest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import message.Message;
import model.CommunicationSocket;

public class ServeurPerroquetGroupe extends Thread implements FacadeTestPerroquet{
	
	private ServerSocket waitForConnectionSocket;
	
	private int port;
	
	private CommunicationSocket commSocket;
	
	public ServeurPerroquetGroupe(int port){
		this.port = port;
		try {
			this.waitForConnectionSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.start();
	}
	
public void run(){
		

			Socket communicationSocket;
			try {
				communicationSocket = this.waitForConnectionSocket.accept();
				System.out.println("perroquet a recu une demande de connexion");
				receiveMessageFromClientAndAnswerToIt(communicationSocket);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		
	}





public void receiveMessageFromClientAndAnswerToIt(Socket socket) {
	CommunicationSocket commSocket = new CommunicationSocket(socket,false);
	Message message;
	try {
		message = (Message) commSocket.getiS().readObject();
		System.out.println("Perroquet a recu le message : "+message.getData());
		if(message.getData().equals("coucou")){
			Message messageToSend = new Message();
			messageToSend.setData("coucou ack");
			commSocket.getoS().writeObject(messageToSend);
			commSocket.getoS().flush();
		}
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
}


/* methodes de dessous NON UTILISEES */
public Object openConnexionWithPerroquet(InetAddress ip, int port) {
	return null;
}

public void sendMessageCoucouToPerroquet(Object o) {
}

public String receiveMessageFromPerroquet(Object o) {
	return null;
}
	
}
