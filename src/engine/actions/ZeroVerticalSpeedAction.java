package engine.actions;

import engine.Action;

/**
 * Sets the vertical speed of the corresponding Entity to zero.
 * 
 * @author Kyle Finke
 *
 */
public class ZeroVerticalSpeedAction extends Action {

	@Override
	public void act() {
		getEntity().setYSpeed(0);

	}
}