package ihm;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

public class UsersWindow extends JFrame implements Observer {
	
	private JList jlist;
	
	private JButton bDisconnect;
	
	private JButton bCreateAGroup;
	
	private ArrayList<InBox> inBoxList;
	
	private DefaultListModel<String> listModel;
	
	public UsersWindow(){ 
		listModel = new DefaultListModel<String>();
		jlist = new JList(listModel);
		bDisconnect = new JButton("Disconnect");
		bCreateAGroup = new JButton("Create a group");
		this.inBoxList = new ArrayList<InBox>();
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
	
	public ArrayList<InBox> getInBoxList(){
		return inBoxList;
	}


	public void update(String name, int rank) {
		if(rank == -1){
			listModel.addElement(name);
		} else{
			listModel.remove(rank);
		}
		
	}
}
