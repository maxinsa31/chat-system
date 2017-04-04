import controller.Controller;
import ihm.View;

public class launchChatSystem {

	public static void main(String[] args) {
		System.out.println("Bienvenue dans notre chat system");
		
		/* Vue */
		View view = new View();
		
		/* Controller */
		Controller controller = new Controller(view);
		
		/* Ajout des actionListener de la vue */
		view.getConnectionWindow().getbConnection().addActionListener(controller);
		
		

	}

}
