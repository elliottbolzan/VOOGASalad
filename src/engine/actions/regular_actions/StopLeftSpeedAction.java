package engine.actions.regular_actions;

import engine.actions.Action;

/**
 * Sets the horizontal speed of the corresponding Entity to zero if it is moving
 * left.
 * 
 * @author Kyle Finke
 *
 */
public class StopLeftSpeedAction extends Action {

	@Override
	public void act() {
		if (getEntity().getXSpeed() < 0) {
			getEntity().setXSpeed(0);
		}
	}
}