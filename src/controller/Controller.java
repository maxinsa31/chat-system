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
		if(arg0.getSource().equals(this.view.getConnectionWindow().getbConnection())){
			
			this.view.connection();
			
			/* ajout des listeners de la vue */
			this.view.getUsersWindow().getbDisconnect().addActionListener(this);
			this.view.getUsersWindow().getjList().addListSelectionListener(this);
			
			/* On declenche les actions au niveau du réseau */
			this.subscriber = new Subscribe();
			this.helloReceptionThread = new HelloReceptionThread(subscriber.getmS());
			
			/* ajout de la UsersWindow comme observer du modele */
			this.helloReceptionThread.addObserver(view.getUsersWindow());
			
		}else if(arg0.getSource().equals(this.view.getUsersWindow().getbDisconnect())){
			
			this.view.disconnection();
			
			/* on arrête l'envoi des messages d'annonce de connexion */
			this.subscriber.getPeriodicHello().cancelPeriodicHello();
			/* on arrête l'ecoute de messages hello */
			this.helloReceptionThread.cancelHelloReceptionThread();
			
		}
	}

	public void valueChanged(ListSelectionEvent e) {
		if (! e.getValueIsAdjusting()){
			
			view.openConversation();
			
		}	
	}

}
