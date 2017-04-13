package model;

import java.util.Observable;

public class ObjectRead extends Observable{

	private String text;
	
	public void setText(String text){
		this.text = text;
		this.setChanged();
		this.notifyObservers(this);
		System.out.println("notifications aux "+this.countObservers()+" observers");
	}
	
	public String getText(){
		return this.text;
	}
	
}
