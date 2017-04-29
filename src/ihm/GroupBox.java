package ihm;

import java.util.ArrayList;

import javax.swing.JCheckBox;

public class GroupBox extends InBox {

	ArrayList<String> members;
	
	public GroupBox(String title, JCheckBox[] users) {
		super(title,false);
		//this.setVisible(false);
		members = new ArrayList<String>();
		String t="";
		for(JCheckBox cB : users){
			if(cB.isSelected()){
				members.add(cB.getName());
				t = t + cB.getText() + ",";
			}
		}
		if(users.length > 0)
			t = t.substring(0, t.length()-1);
		this.setTitle(t);
	}
	
	public boolean isTheSame(GroupBox gB){
		if(this.members.size() != gB.getMembers().size()){
			return false;
		} else{
			for(String member : members){
				if(!gB.getMembers().contains(member))
					return false;
			}
		}
		return true;
	}
	
	public ArrayList<String> getMembers(){
		return members;
	}

}
