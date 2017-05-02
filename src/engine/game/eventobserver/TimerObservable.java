package engine.game.eventobserver;

import engine.game.timer.TimerManager;

/**
 * Part of the Observable Design Pattern for detecting and responding to Events.
 * Uses an instance of TimerManager to control the master time of the game.
 * 
 * @author Matthew Barbano
 *
 */
public class TimerObservable extends EventObservable {
	private TimerManager currentLevelTimerManager;
	
	/**
	 * Calls the super() constructor, which initializes observers as an ArrayList.
	 */
	public TimerObservable() {
		super();
	}
	
	/**
	 * Sets currentLevelTimerManager to point to toAttach.
	 * @param toAttach
	 */
	public void attachCurrentLevelTimerManager(TimerManager toAttach) {
		currentLevelTimerManager = toAttach;
	}
	
	/**
	 * Returns the current game time in milliseconds.
	 * 
	 * @return game time in milliseconds
	 */
	public int getTimeInMilliseconds() {
		return currentLevelTimerManager.getMilliseconds();
	}
	
	/**
	 * Increments game time by millis milliseconds. Note this is
	 * for special cases and updateObservers() should be called
	 * for the routine increment of time on every iteration of the
	 * game loop.
	 * 
	 * @param millis
	 */
	public void incrementTimeInMilliseconds(int millis) {
		currentLevelTimerManager.incrementTime(millis);
	}
	
	/**
	 * Calls currentLevelTimerManager.tick(), which
	 * increments the time by the duration of each
	 * frame of the game.
	 */
	@Override
	public void updateObservers() {
		currentLevelTimerManager.tick();
	}
}
