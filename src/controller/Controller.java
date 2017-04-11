package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ihm.View;
import model.HelloReceptionThread;
import model.Subscribe;

public class Controller implements ActionListener, ListSelectionListener {
	
	private View view;
	
	private Subscribe subscriber;
	
	private HelloReceptionThread helloReceptionThread;
	
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
			
			view.openConversation();
			
		}	
	}

}
