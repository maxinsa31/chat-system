package model;

import java.util.TimerTask;

import observer.Observable;
import observer.Observer;

public class TaskTimer extends TimerTask implements Observable{

	private Observer obs;
	
	private String name;
	
	public TaskTimer(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		notifyObservers(this);
	}

	public void addObserver(Observer obs) {
		this.obs = obs;
	}

	public void notifyObservers(Object o) {
		obs.update(o);
	}
	
	public String getName(){
		return name;
	}

}
