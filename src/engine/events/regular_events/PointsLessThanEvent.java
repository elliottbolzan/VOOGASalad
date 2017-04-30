package engine.events.regular_events;

import engine.Parameter;
import engine.events.Event;

/**
 * React to points being less than a certain amount
 * 
 * @author nikita
 */
public class PointsLessThanEvent extends Event {

	public PointsLessThanEvent() {
		addParam(new Parameter("Amount of Points", int.class, 0));
	}

	@Override
	public boolean act() {
		return Integer.parseInt(getGameInfo().getScorebar().getScore()) < (int) getParam("Amount of Points");
	}
}
