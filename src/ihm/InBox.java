package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import model.ObjectRead;

public class InBox extends JFrame implements WindowListener, java.util.Observer{

	private String title;
	
	private JButton bSend;
	
	private JTextArea discussion;
	
	private JTextArea textToSend;
	
	private String conv= "";
	
	public InBox(String title, boolean affiche){
		bSend = new JButton("Send");
		discussion = new JTextArea(10, 40);
		textToSend = new JTextArea(3,30);
		this.title = title;
		
		initComponents(affiche);
	}
	
	private void initComponents(boolean affiche){
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
		this.setVisible(affiche);
	}
	
	public String getTextToSend(){
		return textToSend.getText();
	}

	
	public JButton getbSend() {
		return bSend;
	}

	public String getTitle() {
		return title;
	}
	
	public void windowClosing(WindowEvent arg0) {
		this.setVisible(false);
	}
	
	public void update(Observable o, Object arg) {
		if(arg instanceof ObjectRead){
			if(conv.equals("")){
				this.conv = this.title+" : "+((ObjectRead) arg).getText();
			}else{
				this.conv = conv+"\n"+this.title+" : "+((ObjectRead) arg).getText();
			}
			this.discussion.setText(conv);
		}
		if(!this.isVisible())
			this.setVisible(true);
	}
	
	public void setMajSend(){
		if(conv.equals("")){
			this.conv = "moi : "+this.getTextToSend();
		}else{
			this.conv = conv+"\n"+"moi : "+this.getTextToSend();
		}
		this.discussion.setText(conv);
		this.textToSend.setText("");
	}

	public void windowActivated(WindowEvent arg0) {
		
	}

	public void windowClosed(WindowEvent arg0) {
		
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
