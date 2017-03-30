package ihm;

import java.util.ArrayList;

public class View {

	private ConnectionWindow cW;
	
	private UsersWindow uW;
	
	private ArrayList<InBox> inBoxList;
	
	public View(){
		this.inBoxList = new ArrayList<InBox>();
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
	
	public void openConversation(){
		InBox i = findConversation((String)uW.getjList().getSelectedValue());
		if( i == null ){ 
			System.out.println("Creation fenetre");
			inBoxList.add(new InBox((String)uW.getjList().getSelectedValue()));
		}else{
			System.out.println("SetVisible = true ");
			i.setVisible(true);
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
	
	
	
	
	public ConnectionWindow getConnectionWindow(){
		return this.cW;
	}
	
	public UsersWindow getUsersWindow(){
		return this.uW;
	}
}
