package engine.events;

import engine.Event;
import engine.Parameter;

public class TimerEvent extends Event {

	public TimerEvent() {
		addParam(new Parameter("Time", Integer.class, 0));
		addParam(new Parameter("Less Than", Boolean.class, true));
	}

	@Override
	public boolean act() {
		int time = 0;
		try{
			time = getGameInfo().getObservableBundle().getTimerObservable().getTimeInMilliseconds();
		}
		catch(NullPointerException e){ 
			System.out.println("Game Info = " + getGameInfo());
		}
		System.out.println((Boolean)getParam("Less Than"));
		if ((Boolean) getParam("Less Than")) {
			System.out.println(time <= (Integer) getParam("Time"));
			return time <= (Integer) getParam("Time");
		} else {
			return time > (Integer) getParam("Time");
		}
	}
}
