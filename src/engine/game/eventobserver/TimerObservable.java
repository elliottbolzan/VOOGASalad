package engine.game.eventobserver;

import engine.game.timer.TimerManager;

public class TimerObservable extends EventObservable {
	private TimerManager currentLevelTimerManager;

	public TimerObservable() {
		super();
	}

	public void attachCurrentLevelTimerManager(TimerManager toAttach) {
		currentLevelTimerManager = toAttach;
	}

	public int getTimeInMilliseconds() {
		return currentLevelTimerManager.getMilliseconds();
	}

	public void incrementTimeInMilliseconds(int millis) {
		currentLevelTimerManager.incrementTime(millis);
	}

	@Override
	public void updateObservers() {
		currentLevelTimerManager.tick();
	}
}
