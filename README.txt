Projet : Chat System

Auteurs : Maxime FARGEAS, Lionel SOUKARIE

Version Java requise : 1.8

Fonctions implémentées:
	- connexion (avertissement aux autres utilisateurs de ma connexion)
	- deconnexion (avertissement aux autres utilisateurs de ma deconnexion)
	- envoi de messages
	- réception de messages
	- IHM qui permet de sélectionner un destinataire parmi une liste d'utilisateurs connectés
	- IHM qui permet aussi de créer un groupe de destinataires
	 -> MAIS FONCTION D'ENVOI DE MESSAGES A CE GROUPE DE DESTINATAIRES NON IMPLEMENTEE
	- notification de réception d'un message : affichage du message dans la fenêtre de discussion qui apparaît 
		à l'écran.
		
Pour tester notre projet :
	* en localhost :
		- Dans launchChatSystem.java (default package) :
			-> modifier myIp (mettre votre adresse IP en wi-fi ou bien InetAddress.getLocalHost())
			-> modifier myServerPort : chaque application doit avoir un numéro de port différent (sinon erreur du
				style : "numero de port deja utilisé en ce moment"). Par exemple : 50643 pour une appli et 50644 pour l'autre
	
	* en wi-fi sur deux (ou plus) ordinateurs :
		- Dans launchChatSystem.java (default package) :
			-> modifier myIp : récupérer votre adresse IP du réseau Wi-Fi auquel vous êtes connecté et écrivez
				ceci par exemple si votre adresse IP est 192.168.1.1 : myIp = InetAddress.getByName("192.168.1.1";)