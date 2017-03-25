package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class InBox extends JFrame implements WindowListener{
 
	private String title;
	
	private JButton bSend;
	
	private JTextArea discussion;
	
	private JTextArea textToSend;
	
	public InBox(String title){
		bSend = new JButton("Send");
		discussion = new JTextArea(10, 40);
		textToSend = new JTextArea(3,30);
		this.title = title;
		
		initComponents();
	}
	
	private void initComponents(){
		this.setTitle(title);
		/* centre la fen�tre */
		this.setLocationRelativeTo(null);
		
		discussion.setEditable(false);
		discussion.setBackground(Color.getHSBColor(50, 200, 207));
		/* ajout des composants */
		this.add(discussion,BorderLayout.NORTH);
		this.add(textToSend,BorderLayout.WEST);
		this.add(this.bSend, BorderLayout.EAST);
		
		/* ajustement des composants dans la fenetre */
		this.pack();
		/* fen�tre visible */
		this.setSize(400, 300);
		this.setVisible(true);
		
		bSend.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				/* envoyer le message via le r�seau */
			}
		});
		
		
	}

	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		this.setVisible(false);
	}

	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
