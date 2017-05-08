Projet : Chat System

Auteurs : Maxime FARGEAS, Lionel SOUKARIE

Version Java requise : 1.8

Fonctions implementees:
	- connexion (avertissement aux autres utilisateurs de ma connexion)
	- deconnexion (avertissement aux autres utilisateurs de ma deconnexion)
	- envoi de messages
	- reception de messages
	- IHM qui permet de selectionner un destinataire parmi une liste d'utilisateurs connectes
	- IHM qui permet aussi de creer un groupe de destinataires
	 -> MAIS FONCTION D'ENVOI DE MESSAGES A CE GROUPE DE DESTINATAIRES NON IMPLEMENTEE
	- notification de reception d'un message : affichage du message dans la fenetre de discussion qui apparait 
		a l'ecran.
		
Pour tester notre projet :
	* avant toute chose :
		Une fois le projet recupere du git, un point d'exclamation rouge s'affiche (sur eclipse).
		Il faut faire un clic droit sur le projet dans Package Explorer (a gauche dans elcipse)
		puis build Path > Configure Build Path.
		Dans l'onglet Libraries --> Remove "Chat.jar" puis Add External Jars --> selectionner "Chat.jar" à la racine de
		notre dossier.
		
	* en localhost :
		- Dans launchChatSystem.java (default package) :
			-> modifier myIp (mettre votre adresse IP en wi-fi ou bien InetAddress.getLocalHost())
			-> modifier myServerPort : chaque application doit avoir un numero de port different (sinon erreur du
				style : "numero de port deja utilise en ce moment"). Par exemple : 50643 pour une appli et 50644 pour l'autre
	
	* en wi-fi sur deux (ou plus) ordinateurs :
		- Dans launchChatSystem.java (default package) :
			-> modifier myIp : recuperer votre adresse IP du reseau Wi-Fi auquel vous etes connecte et ecrivez
				ceci par exemple si votre adresse IP est 192.168.1.1 : myIp = InetAddress.getByName("192.168.1.1";)