package model;

import java.util.HashMap;
import java.util.Timer;

public class Timers {
	
	private HashMap<String, Timer> timers;
	
	public Timers(){
		this.timers = new HashMap<String, Timer>();
	}
	
	public void launchTimer(String name, HelloReceptionThread hrt){
		System.out.println(name+" lance");
		Timer timer = new Timer();
		TaskTimer taskTimer = new TaskTimer(name);
		taskTimer.addObserver(hrt);
		timer.schedule(taskTimer, 6000l);
		timers.put(name, timer);
	}
	
	public void refreshTimer(String name, HelloReceptionThread hrt){
		System.out.println(name+" raffraichi");
		Timer timer = timers.get(name);
		timer.cancel();
		timer.purge();
		TaskTimer taskTimer = new TaskTimer(name);
		taskTimer.addObserver(hrt);
		Timer newTimer = new Timer();
		newTimer.schedule(taskTimer, 6000l);
		timers.put(name, newTimer);
	}
	
	public void deleteTimer(String name){
		System.out.println(name+" stoppe");
		Timer timer = timers.remove(name);
		timer.cancel();
		timer.purge();
	}
	
}
