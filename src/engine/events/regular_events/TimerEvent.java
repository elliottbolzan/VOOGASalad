package engine.events.regular_events;

import engine.Event;
import engine.Parameter;

/**
 * Contains two parameters: Time and Less Than. Time represents the time at
 * which the TimerEvent changes the value returned by act(). If Less Than is
 * True, act() return True when Time is less than the time for the game. If Less
 * Than is False, act() returns True when time is greater that the time for the
 * game.
 * 
 * @author Matthew Barbano, Kyle Finke
 *
 */
public class TimerEvent extends Event {

	public TimerEvent() {
		addParam(new Parameter("Time", Integer.class, 0));
		addParam(new Parameter("Less Than", Boolean.class, true));
	}

	@Override
	public boolean act() {
		int time = 0;
		try {
			time = getGameInfo().getObservableBundle().getTimerObservable().getTimeInMilliseconds();
		} catch (NullPointerException e) {
			System.out.println("Game Info = " + getGameInfo());
		}
		System.out.println((Boolean) getParam("Less Than"));
		if ((Boolean) getParam("Less Than")) {
			System.out.println(time <= (Integer) getParam("Time"));
			return time <= (Integer) getParam("Time");
		} else {
			return time > (Integer) getParam("Time");
		}
	}
}
