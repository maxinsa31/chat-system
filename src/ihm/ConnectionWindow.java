package ihm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import network.HelloReceptionThread;
import network.Subscribe;

public class ConnectionWindow extends JFrame{
	
	private JButton bConnection;
	
	public ConnectionWindow(){
		bConnection = new JButton("Connection");
		initComponents();
	}
	
	private void initComponents(){
		this.setTitle("ChatSystem");
		/* centre la fen�tre */
		this.setLocationRelativeTo(null);
		/* comportement lors de la fermeture de la fen�tre */
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		/* ajout des composants */
		this.add(bConnection,BorderLayout.SOUTH);
		
		/* ajustement des composants dans la fenetre */
		this.pack();
		/* fen�tre visible */
		this.setSize(400, 400);
		this.setVisible(true);
		
		bConnection.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				Subscribe s = new Subscribe();
				new UsersWindow(s.getmS());
			}
		});
	}
}
