Projet : Chat System

Auteurs : Maxime FARGEAS, Lionel SOUKARIE

Version Java requise : 1.8

Fonctions impl�ment�es:
	- connexion (avertissement aux autres utilisateurs de ma connexion)
	- deconnexion (avertissement aux autres utilisateurs de ma deconnexion)
	- envoi de messages
	- r�ception de messages
	- IHM qui permet de s�lectionner un destinataire parmi une liste d'utilisateurs connect�s
	- IHM qui permet aussi de cr�er un groupe de destinataires
	 -> MAIS FONCTION D'ENVOI DE MESSAGES A CE GROUPE DE DESTINATAIRES NON IMPLEMENTEE
	- notification de r�ception d'un message : affichage du message dans la fen�tre de discussion qui appara�t 
		� l'�cran.
		
Pour tester notre projet :
	* en localhost :
		- Dans launchChatSystem.java (default package) :
			-> modifier myIp (mettre votre adresse IP en wi-fi ou bien InetAddress.getLocalHost())
			-> modifier myServerPort : chaque application doit avoir un num�ro de port diff�rent (sinon erreur du
				style : "numero de port deja utilis� en ce moment"). Par exemple : 50643 pour une appli et 50644 pour l'autre
	
	* en wi-fi sur deux (ou plus) ordinateurs :
		- Dans launchChatSystem.java (default package) :
			-> modifier myIp : r�cup�rer votre adresse IP du r�seau Wi-Fi auquel vous �tes connect� et �crivez
				ceci par exemple si votre adresse IP est 192.168.1.1 : myIp = InetAddress.getByName("192.168.1.1";)