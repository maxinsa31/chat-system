package ihm;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ConnectionWindow extends JFrame{
	
	private JButton bConnection;
	
	public ConnectionWindow(){
		bConnection = new JButton("Connection");
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
		
		/* ajustement des composants dans la fenetre */
		this.pack();
		/* fenetre visible */
		this.setSize(400, 400);
		this.setVisible(true);
	}

	public JButton getbConnection() {
		return bConnection;
	}

}
