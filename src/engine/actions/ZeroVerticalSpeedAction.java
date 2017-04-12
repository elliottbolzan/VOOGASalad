package engine.actions;

import engine.Action;

public class ZeroVerticalSpeedAction extends Action {

	@Override
	public void act() {
		if(getEntity().getYSpeed() > 0) {
			getEntity().setYSpeed(0);
			getEntity().setYAcceleration(0);
			System.out.println("CALLED");
		}
	}
}