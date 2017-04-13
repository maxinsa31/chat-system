package ihm;

import java.util.ArrayList;

public class View {

	private ConnectionWindow cW;
	
	private UsersWindow uW;
	
	private ArrayList<InBox> inBoxList;
	
	private ArrayList<GroupBox> groupBoxList;
	
	private GroupSelectionWindow groupSelectionWindow;
	
	public View(){
		this.inBoxList = new ArrayList<InBox>();
		this.groupBoxList = new ArrayList<GroupBox>();
		this.cW = new ConnectionWindow();
	}
	
	public void connection(){
		/* on cache la fenetre de connexion */
		this.cW.setVisible(false);
		/* on créé une nouvelle fenetre avec la liste d'utilisateurs */
		this.uW = new UsersWindow();
	}
	
	public void disconnection(){
		/* fermeture de la fenetre avec la liste d'utilisateurs */
		this.uW.dispose();
		/* on fait reapparaitre la fenetre de connexion */
		this.cW.setVisible(true);
	}
	
	public InBox openConversation(){
		InBox i = findConversation((String)uW.getjList().getSelectedValue());
		if( i == null ){ 
			InBox created = new InBox((String)uW.getjList().getSelectedValue());
			inBoxList.add(created);
			return created;
		}else{
			i.setVisible(true);
			return i;
		}
	}
	
	
	public InBox findConversation(String title){
		for( InBox i : this.inBoxList){
			if (i.getTitle().equals(title)){
				return i;
			}
		}
		return null;
	}
	
	public InBox createConversation(String title){
		InBox i = new InBox(title);
		i.setVisible(false);
		inBoxList.add(i);
		return i;
	}
	
	public void openGroupSelectionWindow(){
		groupSelectionWindow = new GroupSelectionWindow(this.uW.getListModel());
	}
	
	public void openGroupConversation(){
		
		if(this.groupSelectionWindow.possibleToCreate()){ /* si au moins 2 utilisateurs ont été selectionnes */
			
			/* fermeture de la fenetre de selection des membres */
			this.groupSelectionWindow.dispose();
	
			GroupBox selected = new GroupBox("", this.groupSelectionWindow.getUsers());
			boolean exists = false;
			/* verification de la non existence de ce groupe */
			for(GroupBox gB : groupBoxList){
				if(selected.isTheSame(gB)){ /* le groupe existe deja donc on ne fait que faire reapparaitre la conversation de groupe */
					selected.dispose();
					gB.setVisible(true);
					exists = true;
				}
			}
			if(!exists){ /* le groupe n'existe pas donc on le cree*/
				groupBoxList.add(selected);
				selected.setVisible(true);
			}
			
		} else{
			// TODO : ouverture d'une fenetre d'erreur pour indiquer que le nombre de destinataires doit etre superieur à 2 
		}
		
		
	}
	
	
	public ConnectionWindow getConnectionWindow(){
		return this.cW;
	}
	
	public UsersWindow getUsersWindow(){
		return this.uW;
	}
	
	public GroupSelectionWindow getGroupSelectionWindow(){
		return this.groupSelectionWindow;
	}
}
