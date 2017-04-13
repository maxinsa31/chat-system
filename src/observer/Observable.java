package observer;

public interface Observable {
	
	public void addObserver(Observer obs);
	
	public void notifyObservers(Object o);
	
}
