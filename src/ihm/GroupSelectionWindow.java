package ihm;

import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

public class GroupSelectionWindow extends JFrame{

	private JCheckBox[] users;
	
	private DefaultListModel<String> list;
	
	private JButton bFinish;
	
	public GroupSelectionWindow(DefaultListModel<String> list){
		this.list = list;
		this.bFinish = new JButton("Finish");
		int size = list.size();
		this.setLayout(new GridLayout(size+1, 1));
		int index=0;
		users = new JCheckBox[size];
		while(index < size){
			users[index] = new JCheckBox(list.getElementAt(index));
			System.out.println("Ajout du bouton="+list.getElementAt(index));
			this.add(users[index]);
			index++;
		}
		this.add(this.bFinish);
		this.setTitle("Selection of users");
		this.pack();
		this.setVisible(true);
	}
	
	public boolean possibleToCreate(){
		int cpt=0;
		for(JCheckBox cB : users){
			if(cB.isSelected()){
				cpt++;
			}
		}
		return (cpt>=2);
	}
	
	public JButton getbFinish(){
		return bFinish;
	}
	
	public JCheckBox[] getUsers(){
		return users;
	}
}
