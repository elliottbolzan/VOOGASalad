package engine.actions.regular_actions;

import engine.Parameter;
import engine.actions.Action;

/**
 * Set the width and height of the Entity to new values.
 * 
 * @author Jay Doherty
 *
 */
public class EntityResizeAction extends Action {

	public EntityResizeAction() {
		addParam(new Parameter(getResource("Width"), double.class, 0.0));
		addParam(new Parameter(getResource("Height"), double.class, 0.0));
	}

	@Override
	public void act() {
		if (((double) getParam(getResource("Width"))) != getEntity().getWidth()
				|| ((double) getParam(getResource("Height"))) != getEntity().getHeight()) {
			getEntity().setWidth((double) getParam(getResource("Width")));
			getEntity().setHeight((double) getParam(getResource("Height")));
			this.getGameInfo().getGraphicsEngine().updateView();
		}
	}

}
