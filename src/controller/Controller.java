package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ihm.InBox;
import ihm.View;
import model.CommunicationServer;
import model.Clients;
import model.CommunicationClient;
import model.CommunicationSocket;
import model.HelloReceptionThread;
import model.Subscribe;
import observer.Observer;

public class Controller implements ActionListener, ListSelectionListener, Observer {
	
	private View view;
	
	private Subscribe subscriber;
	
	private HelloReceptionThread helloReceptionThread;
	
	private CommunicationServer commServer;
	
	private Clients clients;
	
	
	public Controller(View view){
		this.view = view;
	}

	public void actionPerformed(ActionEvent arg0) {
		/* Login à enregistrer */
		String login = this.view.getConnectionWindow().getLogin();
		if(arg0.getSource().equals(this.view.getConnectionWindow().getbConnection())){ /* Appui bouton connexion */
			
			this.view.connection();
			
			/* ajout des listeners de la vue */
			this.view.getUsersWindow().getbDisconnect().addActionListener(this);
			this.view.getUsersWindow().getjList().addListSelectionListener(this);
			this.view.getUsersWindow().getbCreateAGroup().addActionListener(this);
			
			
			/* On declenche les actions au niveau du réseau */
			this.subscriber = new Subscribe(login);
			this.helloReceptionThread = new HelloReceptionThread(subscriber.getmS(),login);
			
			/* ajout de la UsersWindow comme observer du modele */
			this.helloReceptionThread.addObserver(view.getUsersWindow());
			
			/* creation de la classe qui stocke l'ensemble des CommunicationClient que l'on est */
			clients = new Clients();
			
			/* lancement du serveur d'écoute de demande de connexion TCP pour communiquer */
			commServer = new CommunicationServer();
			System.out.println("(Controller) je m'ajoute comme observateur pour le communicationServer");
			commServer.addObserver(this);
			
			
		}else if(arg0.getSource().equals(this.view.getUsersWindow().getbDisconnect())){ /* Appui bouton deconnexion */
			
			this.view.disconnection();
			
			/* on arrête l'envoi des messages d'annonce de connexion */
			this.subscriber.getPeriodicHello().cancelPeriodicHello();
			/* on arrête l'ecoute de messages hello */
			this.helloReceptionThread.cancelHelloReceptionThread();
			/* on coupe le serveur d'ecoute de demande de connexion TCP */
			this.commServer.cancelCommunicationServer();
			
		} else if(arg0.getSource().equals(view.getUsersWindow().getbCreateAGroup())){ /* Appui bouton creation de groupe */
			
			this.view.openGroupSelectionWindow();
			
			/* Ajout du listener pour le bouton Terminer */
			this.view.getGroupSelectionWindow().getbFinish().addActionListener(this);
			
		} else if(arg0.getSource().equals(view.getGroupSelectionWindow().getbFinish())){ /* Appui bouton Terminer creation de groupe */
			
			this.view.openGroupConversation();
			
		}
		
	}

	public void valueChanged(ListSelectionEvent e) {
		if (! e.getValueIsAdjusting()){
			InBox iB = view.findConversation((String)view.getUsersWindow().getjList().getSelectedValue());
			
			/* ouverture de la fenetre de conversation */
			final InBox i = view.openConversation();
			
			
			/* variables intermediaires relatives a la fenetre de conversation */
			String pseudo = i.getTitle();
			final InetAddress ipAddress = helloReceptionThread.getIpAddressOf(pseudo);
			final boolean isServer = commServer.socketServerExists(ipAddress);
			final boolean isClient = clients.socketClientExists(ipAddress);
			final boolean isServerSocketNeverUsed = commServer.socketServerNeverOpenedExists(ipAddress);
			
			/* si ni un socket serveur ni un socket client est ouvert : on cree un socket client (CommunicationClient) */
			if(ipAddress != null && !isServer && !isClient && !isServerSocketNeverUsed){
				try {
					CommunicationClient cClient = new CommunicationClient(ipAddress, 50644);
					clients.addClient(cClient);
					cClient.getObjRead().addObserver(i);
				} catch (IOException e1) {
					e1.printStackTrace();
				} 
			}
			
			/* ajout de l'action listener pour le bouton bSend s'il n'a jamais ete ajoute */
			if(iB == null || isServerSocketNeverUsed){
				if(isServerSocketNeverUsed){
					CommunicationSocket cS = commServer.findCommunicationSocketNeverUsed(ipAddress);
					commServer.transferFromNeverUsedToUsed(cS);
				}
				
				i.getbSend().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.out.println("Appui sur le bouton send");
						if(isServerSocketNeverUsed){
							CommunicationSocket socket = commServer.findCommunicationSocket(ipAddress);
							if(socket != null){
								try {
									socket.getBuffWrite().write(i.getTextToSend()+"\n");
									socket.getBuffWrite().flush();
									System.out.println("(Server):j'envoi le messgae : "+i.getTextToSend());
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						} else{ //isClient					
							CommunicationSocket socket = clients.findCommunicationSocket(ipAddress);
							if(socket != null){
								try{
									socket.getBuffWrite().write(i.getTextToSend()+"\n");
									socket.getBuffWrite().flush();
									System.out.println("(Client):j'envoi le messgae : "+i.getTextToSend());
								} catch (IOException e1) {
									e1.printStackTrace();
								}		
							}
						}
					}
				});

				
			}
		}	
	}

	public void update(Object o) {
		if(o instanceof CommunicationSocket){
			System.out.println("(Controller/update) @IP du remote host = "+((CommunicationSocket)o).getRemoteIpAddress());
			String pseudo = helloReceptionThread.getPseudoOf(((CommunicationSocket)o).getRemoteIpAddress());
			System.out.println("(Controller/udpate) ajout d'une InBox au pseudo = "+pseudo);
			InBox i = view.createConversation(pseudo);
			((CommunicationSocket)o).getObjRead().addObserver(i);
			System.out.println("(Controller/udpate) nb obervers = "+((CommunicationSocket)o).getObjRead().countObservers());
		}		
	}
}

