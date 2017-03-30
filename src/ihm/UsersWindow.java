package ihm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MulticastSocket;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.HelloReceptionThread;
import model.Subscribe;

public class UsersWindow extends JFrame implements Observer {
	
	private JList jlist;
	
	private JButton bDisconnect;
	
	private ArrayList<InBox> inBoxList;
	
	private DefaultListModel<String> listModel;;
	
	public UsersWindow(){
		listModel = new DefaultListModel<String>();
		jlist = new JList(listModel);
		bDisconnect = new JButton("Disconnect");
		this.inBoxList = new ArrayList<InBox>();
		initComponents();
	}
		
	
	private void initComponents(){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("ChatSystem");
		/* centre la fenetre */
		this.setLocationRelativeTo(null);
				
		
		/* ajout des composants */
		this.add(bDisconnect,BorderLayout.SOUTH);
		this.add(jlist);
		
		/* ajustement des composants dans la fenetre */
		this.pack();
		
		/* fenetre visible */
		this.setSize(200, 400);
		this.setVisible(true);
		
		/*bDisconnect.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				 fermeture de la fenêtre de la liste d'utilisateur 
				dispose();
				 coupe le PeriodicHello 
				s.getPeriodicHello().cancelPeriodicHello();
				 ouverture d'une nouvelle fenêtre de connexion 
				new ConnectionWindow();
			}
		});*/
		
		/*jlist.addListSelectionListener(new ListSelectionListener() {
			
			
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if (! e.getValueIsAdjusting()){
					InBox i = exist((String)jlist.getSelectedValue());
					if( i == null ){ 
						System.out.println("Creation fenetre");
						inBoxList.add(new InBox((String)jlist.getSelectedValue()));
					}else{
						System.out.println("SetVisible = true ");
						i.setVisible(true);
					}
				}
			}
		});*/
	}
	
	
	public JButton getbDisconnect() {
		return bDisconnect;
	}
	
	public JList getjList(){
		return jlist;
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
