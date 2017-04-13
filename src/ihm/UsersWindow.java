package ihm;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import model.PseudoToRank;
import observer.Observer;

public class UsersWindow extends JFrame implements Observer {
	
	private JList jlist;
	
	private JButton bDisconnect;
	
	private JButton bCreateAGroup;
	
	private DefaultListModel<String> listModel;
	
	public UsersWindow(){ 
		listModel = new DefaultListModel<String>();
		jlist = new JList(listModel);
		bDisconnect = new JButton("Disconnect");
		bCreateAGroup = new JButton("Create a group");
		initComponents();
	}
		
	
	private void initComponents(){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("ChatSystem");
		/* centre la fenetre */
		this.setLocationRelativeTo(null);
		
		this.setLayout(new BorderLayout());
		
		JPanel pan = new JPanel(new GridLayout(7,1));
		pan.add(bCreateAGroup);
		
				
		
		/* ajout des composants */
		this.add(jlist,BorderLayout.CENTER);
		this.add(bDisconnect,BorderLayout.SOUTH);
		this.add(pan, BorderLayout.EAST);
	
		
		/* ajustement des composants dans la fenetre */
		this.pack();
		
		/* fenetre visible */
		this.setSize(400, 400);
		this.setVisible(true);		
		
	}
	
	

	
	public JButton getbDisconnect() {
		return bDisconnect;
	}
	
	public JButton getbCreateAGroup(){
		return bCreateAGroup;
	}
	
	public JList getjList(){
		return jlist;
	}
	
	public DefaultListModel<String> getListModel(){
		return listModel;
	}

	public void update(Object o) {
		if(o instanceof PseudoToRank){
			if(((PseudoToRank)o).getRank() == -1){
				listModel.addElement(((PseudoToRank)o).getPseudo());
			} else{
				listModel.remove(((PseudoToRank)o).getRank());
			}
		}
	}
}
