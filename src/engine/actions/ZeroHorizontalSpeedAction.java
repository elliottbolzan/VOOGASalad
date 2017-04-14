package engine.actions;

import engine.Action;

/**
 * Sets the horizontal speed of the corresponding Entity to zero.
 * 
 * @author Kyle Finke
 *
 */
public class ZeroHorizontalSpeedAction extends Action {

	@Override
	public void act() {
		getEntity().setXSpeed(0);
	}
}