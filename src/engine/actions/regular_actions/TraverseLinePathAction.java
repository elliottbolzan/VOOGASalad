package engine.actions.regular_actions;

import engine.Parameter;
import engine.actions.Action;
import engine.actions.TraverseLineHelper;
import engine.actions.TraversePathHelper;
import javafx.geometry.Point2D;

/**
 * Traverses a path between the starting and ending points.
 * 
 * @author Matthew Barbano
 *
 */

public class TraverseLinePathAction extends Action {
	private TraversePathHelper traverser;
	private boolean firstTime;

	public TraverseLinePathAction() {
		addParam(new Parameter(getResource("EndX"), Double.class, 0.0));
		addParam(new Parameter(getResource("EndY"), Double.class, 0.0));
		addParam(new Parameter(getResource("TraversalSpeed"), Double.class, 0.0));
		addParam(new Parameter(getResource("Reversible"), Boolean.class, true));
		firstTime = true;
	}

	@Override
	public void act() {
		if (firstTime) {
			traverser = new TraverseLineHelper(
					new Point2D((double) getParam(getResource("EndX")), (double) getParam(getResource("EndY"))),
					Math.abs((double) getParam(getResource("TraversalSpeed"))), getEntity(),
					(boolean) getParam(getResource("Reversible")));
			firstTime = false;
		}
		traverser.updatePhysics();
	}

}
