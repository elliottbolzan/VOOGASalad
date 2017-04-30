package engine.actions.regular_actions;

import engine.actions.Action;

/**
 * Sets the vertical speed of the corresponding Entity to zero.
 * 
 * @author Kyle Finke
 *
 */
public class StopUpSpeedAction extends Action {

	@Override
	public void act() {
		if (getEntity().getYSpeed() < 0) {
			getEntity().setYSpeed(0);
		}
	}
}