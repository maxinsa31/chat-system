package ihm;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ConnectionWindow extends JFrame{
	
	private JButton bConnection;
	
	private JTextArea login;
	
	public ConnectionWindow(){
		bConnection = new JButton("Connection");
		login = new JTextArea(1, 20);
		initComponents();
	}
	
	private void initComponents(){
		this.setTitle("ChatSystem");
		/* centre la fenetre */
		this.setLocationRelativeTo(null);
		/* comportement lors de la fermeture de la fenetre */
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		/* ajout des composants */
		this.add(bConnection,BorderLayout.SOUTH);
		this.add(login,BorderLayout.NORTH);
		
		/* ajustement des composants dans la fenetre */
		this.pack();
		/* fenetre visible */
		this.setSize(400, 400);
		this.setVisible(true);
	}

	public JButton getbConnection() {
		return bConnection;
	}
	
	public String getLogin(){
		return this.login.getText();
	}

}
