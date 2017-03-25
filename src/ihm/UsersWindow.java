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

import network.HelloReceptionThread;

public class UsersWindow extends JFrame {
	
	private DefaultListModel<String> list;
	
	private JList jlist;
	
	private JButton bDisconnect;
	
	private ArrayList<InBox> inBoxList;
	
	private HelloReceptionThread hRT;
	
	public UsersWindow(MulticastSocket mS){
		list = new DefaultListModel<String>();
		jlist = new JList(list);
		hRT = new HelloReceptionThread(list,mS);
		bDisconnect = new JButton("Disconnect");
		this.inBoxList = new ArrayList<InBox>();
		initComponents();
	}
	
	private InBox exist(String title){
		for( InBox i : this.inBoxList){
			if (i.getTitle().equals(title)){
				return i;
			}
		}
		return null;
	}
	
	
	
	
	private void initComponents(){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("ChatSystem");
		/* centre la fen�tre */
		this.setLocationRelativeTo(null);
				
		
		/* ajout des composants */
		this.add(bDisconnect,BorderLayout.SOUTH);
		this.add(jlist);
		
		/* ajustement des composants dans la fenetre */
		this.pack();
		/* fen�tre visible */
		this.setSize(200, 400);
		this.setVisible(true);
		
		bDisconnect.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				/////////////////////////////////////////////////////TODO Couper le periodicHello
				dispose();
				new ConnectionWindow();
			}
		});
		
		jlist.addListSelectionListener(new ListSelectionListener() {
			
			
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
		});
	}
}
