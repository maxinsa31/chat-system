package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ihm.GroupBox;
import ihm.InBox;
import ihm.View;
import message.Message;
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
	
	private InetAddress myIp;
	
	private int myServerPort;
	
	private int myMulticastPort;
	
	
	public Controller(View view, InetAddress myIp, int myServerPort, int myMulticastPort){
		this.view = view;
		this.myIp = myIp;
		this.myServerPort = myServerPort;
		this.myMulticastPort = myMulticastPort;
	}

	public void actionPerformed(ActionEvent arg0) {
		/* Login � enregistrer */
		String login = this.view.getConnectionWindow().getLogin();
		if(arg0.getSource().equals(this.view.getConnectionWindow().getbConnection())){ /* Appui bouton connexion */
			
			this.view.connection();
			
			/* ajout des listeners de la vue */
			this.view.getUsersWindow().getbDisconnect().addActionListener(this);
			this.view.getUsersWindow().getjList().addListSelectionListener(this);
			this.view.getUsersWindow().getbCreateAGroup().addActionListener(this);
			
			
			/* On declenche les actions au niveau du r�seau */
			this.subscriber = new Subscribe(login,this.myIp,this.myServerPort,this.myMulticastPort);
			this.subscriber.getPeriodicHello().start();
			this.helloReceptionThread = new HelloReceptionThread(subscriber.getmS(),login);
			this.helloReceptionThread.start();
			
			/* ajout de la UsersWindow comme observer du modele */
			this.helloReceptionThread.addObserver(view.getUsersWindow());
			
			/* creation de la classe qui stocke l'ensemble des CommunicationClient que l'on est */
			clients = new Clients();
			
			/* lancement du serveur d'�coute de demande de connexion TCP pour communiquer */
			commServer = new CommunicationServer(this.myServerPort);
			//System.out.println("(Controller) je m'ajoute comme observateur pour le communicationServer");
			commServer.addObserver(this);
			
			
		}else if(arg0.getSource().equals(this.view.getUsersWindow().getbDisconnect())){ /* Appui bouton deconnexion */
			
			this.view.disconnection();
			
			/* on arr�te l'envoi des messages d'annonce de connexion */
			this.subscriber.getPeriodicHello().cancelPeriodicHello();
			/* on arr�te l'ecoute de messages hello */
			this.helloReceptionThread.cancelHelloReceptionThread();
			/* on coupe le serveur d'ecoute de demande de connexion TCP ainsi que tous les sockets ouverts */
			this.commServer.cancelCommunicationServer();
			/* on coupe tous les sockets ouverts en tant que clients */
			this.clients.cancelSockets();
			
		} else if(arg0.getSource().equals(view.getUsersWindow().getbCreateAGroup())){ /* Appui bouton creation de groupe */
			
			this.view.openGroupSelectionWindow();
			
			/* Ajout du listener pour le bouton Terminer */
			this.view.getGroupSelectionWindow().getbFinish().addActionListener(this);
			
		} else if(arg0.getSource().equals(view.getGroupSelectionWindow().getbFinish())){ /* Appui bouton Terminer creation de groupe */
			
			GroupBox gB = this.view.openGroupConversation();
			
			/* si plus de 2 destinataires ont ete selectionnes */
			if(gB != null){
				//TODO :
				/*
				 *  - verifier existence de InBox pour chacun des destinataires
				 *  - si n'existe pas :
				 *  	- creer un InBox pour ceux qui n'en ont pas et ajouter actionListener bSend
				 *  - si la discussion de groupe vient d'�tre creee, on ajoute l'actionListener du bouton bSend
				 *  		-> envoi du message � partir de chacun des sockets
				 */
			}
			
		}
		
	}

	public void valueChanged(ListSelectionEvent e) {
		if (! e.getValueIsAdjusting() && (String)view.getUsersWindow().getjList().getSelectedValue() != null){
			InBox iB = view.findConversation((String)view.getUsersWindow().getjList().getSelectedValue());
			
			/* ouverture de la fenetre de conversation */
			final InBox i = view.openConversation();
			
			/* variables intermediaires relatives a la fenetre de conversation */
			String pseudo = i.getTitle();
			final InetAddress ipAddress = helloReceptionThread.getIpAddressOf(pseudo);
			final int port = helloReceptionThread.getPortOf(pseudo);
			final boolean isServer = commServer.socketServerExists(ipAddress);
			final boolean isClient = clients.socketClientExists(ipAddress);
			
			/* si ni un socket serveur ni un socket client est ouvert : on cree un socket client (CommunicationClient) */
			if(ipAddress != null && !isServer && !isClient){
				try {
					CommunicationClient cClient = new CommunicationClient(ipAddress, port);
					clients.addClient(cClient);
					cClient.getObjRead().addObserver(i);
				} catch (IOException e1) {
					e1.printStackTrace();
				} 
			}
			
			/* ajout de l'action listener pour le bouton bSend s'il n'a jamais ete ajoute */
			if(iB == null){
				
				i.getbSend().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {	
						CommunicationSocket socket = clients.findCommunicationSocket(ipAddress);
						if(socket != null){
							try{
								Message message = new Message();
								message.setData(i.getTextToSend());
								socket.getoS().writeObject(message);
								socket.getoS().flush();
								
								i.setMajSend();
							} catch (IOException e1) {
								e1.printStackTrace();
							}		
						}
					}
				});

				
			}
		}	
	}

	public void update(Object o) {
		if(o instanceof CommunicationSocket){
			String pseudo = helloReceptionThread.getPseudoOf(((CommunicationSocket)o).getRemoteIpAddress());
			final InBox i = view.createConversation(pseudo);
			final InetAddress ipAddress = helloReceptionThread.getIpAddressOf(pseudo);
			final boolean isServerSocketNeverUsed = commServer.socketServerNeverOpenedExists(ipAddress);
			
			if(isServerSocketNeverUsed){
				CommunicationSocket cS = commServer.findCommunicationSocketNeverUsed(ipAddress);
				commServer.transferFromNeverUsedToUsed(cS);
				i.getbSend().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CommunicationSocket socket = commServer.findCommunicationSocket(ipAddress);
						if(socket != null){
							try {
								Message message = new Message();
								message.setData(i.getTextToSend());
								socket.getoS().writeObject(message);
								socket.getoS().flush();
								i.setMajSend();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
				});
				
			}
			((CommunicationSocket)o).getObjRead().addObserver(i);}		
	}
}

