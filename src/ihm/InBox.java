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
		/* centre la fenetre */
		this.setLocationRelativeTo(null);
		
		discussion.setEditable(false);
		discussion.setBackground(Color.getHSBColor(50, 200, 207));
		/* ajout des composants */
		this.add(discussion,BorderLayout.NORTH);
		this.add(textToSend,BorderLayout.WEST);
		this.add(this.bSend, BorderLayout.EAST);
		
		/* ajustement des composants dans la fenetre */
		this.pack();
		/* fenetre visible */
		this.setSize(400, 300);
		this.setVisible(true);
		
		
	}
	
	public JButton getbSend(){
		return bSend;
	}
	
	public String getTextToSend(){
		return textToSend.getText();
	}

	public void windowActivated(WindowEvent arg0) {
		
	}

	public void windowClosed(WindowEvent arg0) {
		
	}

	public void windowClosing(WindowEvent arg0) {
		this.setVisible(false);
	}

	public void windowDeactivated(WindowEvent arg0) {
		
	}

	public void windowDeiconified(WindowEvent arg0) {
		
	}

	public void windowIconified(WindowEvent arg0) {
		
	}

	public void windowOpened(WindowEvent arg0) {
		
	}
}
