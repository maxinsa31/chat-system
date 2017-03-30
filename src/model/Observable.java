package model;

import ihm.Observer;

public interface Observable {
	
	public void addObserver(Observer obs);
	
	public void notifyObservers(String name, int rank);
	
}
