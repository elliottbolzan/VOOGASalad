package engine.actions.regular_actions;

import engine.Parameter;
import engine.actions.Action;

/**
 * Action that moves an entity back and forth at the given step interval.
 * 
 * @author nikita
 */
public class MoveBackAndForthAction extends Action {
	private long stepAmount;
	private boolean firstTime;

	public MoveBackAndForthAction() {
		addParam(new Parameter(getResource("AllowedXSteps"), int.class, 0));
		addParam(new Parameter(getResource("AllowedYSteps"), int.class, 0));
		addParam(new Parameter(getResource("XSpeed"), double.class, 0.0));
		addParam(new Parameter(getResource("YSpeed"), double.class, 0.0));
		stepAmount = 0;
		firstTime = true;
	}

	@Override
	public void act() {
		if (firstTime) {
			getEntity().setXSpeed((double) getParam(getResource("XSpeed")));
			getEntity().setYSpeed((double) getParam(getResource("YSpeed")));
			firstTime = false;
		}
		updateSpeeds();
		stepAmount++;
	}

	private void updateSpeeds() {
		if (((int) getParam(getResource("AllowedXSteps")) != 0)
				&& stepAmount % (int) getParam(getResource("AllowedXSteps")) == 0)
			getEntity().setXSpeed(-getEntity().getXSpeed());
		if (((int) getParam(getResource("AllowedYSteps")) != 0)
				&& stepAmount % (int) getParam(getResource("AllowedYSteps")) == 0)
			getEntity().setYSpeed(-getEntity().getYSpeed());
	}
}
