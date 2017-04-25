package engine.actions;

import engine.Action;
import engine.Parameter;

/**
 * Action that moves an entity back and forth at the given step interval.
 * 
 * @author nikita
 */
public class MoveBackAndForthAction extends Action {
	private long stepAmount;
	private boolean firstTime;

	public MoveBackAndForthAction() {
		addParam(new Parameter("Allowed x steps", int.class, 0));
		addParam(new Parameter("Allowed y steps", int.class, 0));
		addParam(new Parameter("X Speed", double.class, 0.0));
		addParam(new Parameter("Y Speed", double.class, 0.0));
		stepAmount = 0;
		firstTime = true;
	}

	@Override
	public void act() {
		if (firstTime) {
			getEntity().setXSpeed((double) getParam("X Speed"));
			getEntity().setYSpeed((double) getParam("Y Speed"));
			firstTime = false;
		}
		updateSpeeds();
		stepAmount++;
	}

	private void updateSpeeds() {
		if (((int) getParam("Allowed x steps") != 0) && stepAmount % (int) getParam("Allowed x steps") == 0)
			getEntity().setXSpeed(-getEntity().getXSpeed());
		if (((int) getParam("Allowed y steps") != 0) && stepAmount % (int) getParam("Allowed y steps") == 0)
			getEntity().setYSpeed(-getEntity().getYSpeed());
	}
}
