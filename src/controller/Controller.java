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
import model.CommunicationSocket;
import model.HelloReceptionThread;
import model.Subscribe;

public class Controller implements ActionListener, ListSelectionListener {
	
	private View view;
	
	private Subscribe subscriber;
	
	private HelloReceptionThread helloReceptionThread;
	
	private CommunicationServer commServer;
	
	public Controller(View view){
		this.view = view;
	}

	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(this.view.getConnectionWindow().getbConnection())){ /* Appui bouton connexion */
			
			this.view.connection();
			
			/* ajout des listeners de la vue */
			this.view.getUsersWindow().getbDisconnect().addActionListener(this);
			this.view.getUsersWindow().getjList().addListSelectionListener(this);
			this.view.getUsersWindow().getbCreateAGroup().addActionListener(this);
			
			/* Login à enregistrer */
			String login = this.view.getConnectionWindow().getLogin();
			
			/* On declenche les actions au niveau du réseau */
			this.subscriber = new Subscribe(login);
			this.helloReceptionThread = new HelloReceptionThread(subscriber.getmS(),login);
			
			/* ajout de la UsersWindow comme observer du modele */
			this.helloReceptionThread.addObserver(view.getUsersWindow());
			
			/* lancement du serveur d'écoute de demande de connexion TCP pour communiquer */
			commServer = new CommunicationServer();
			
		}else if(arg0.getSource().equals(this.view.getUsersWindow().getbDisconnect())){ /* Appui bouton deconnexion */
			
			this.view.disconnection();
			
			/* on arrête l'envoi des messages d'annonce de connexion */
			this.subscriber.getPeriodicHello().cancelPeriodicHello();
			/* on arrête l'ecoute de messages hello */
			this.helloReceptionThread.cancelHelloReceptionThread();
			
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
			
			/* si ni un socket serveur ni un socket client est ouvert : on cree un socket client (CommunicationClient) */
			//TODO : lors du merge, fusionner les conditions (ajout de la condition sur l'existence d'un socket client pour ce pseudo)
			if(ipAddress != null && !isServer){
				//TODO : creer socket client
			}
			
			/* ajout de l'action listener pour le bouton bSend s'il n'a jamais ete ajoute */
			if(iB == null){
				i.getbSend().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(isServer){
							CommunicationSocket socket = commServer.findCommunicationSocket(ipAddress);
							if(socket != null){
								try {
									socket.getBuffWrite().write(i.getTextToSend());
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						} else{
							//TODO : trouver et utiliser socket client
						}
					}
				});
			}
		}	
	}

}
