package model;

import java.util.Observable;

import message.Message;

public class ObjectRead extends Observable{

	private Message message;
	
	public ObjectRead(){
		this.message = new Message();
	}
	
	public void setText(String text){
		this.message.setData(text);
		this.setChanged();
		this.notifyObservers(this);
	}
	
	public String getText(){
		return this.message.getData();
	}
	
}
