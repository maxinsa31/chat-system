package ihm;

import java.util.ArrayList;

import javax.swing.JCheckBox;

public class GroupBox extends InBox {

	ArrayList<String> members;
	
	public GroupBox(String title, JCheckBox[] users) {
		super(title);
		members = new ArrayList<String>();
		String t="";
		for(JCheckBox cB : users){
			if(cB.isSelected()){
				members.add(cB.getName());
				t = t + cB.getText() + ",";
			}
		}
		t = t.substring(0, t.length()-1);
		this.setTitle(t);
	}

}
