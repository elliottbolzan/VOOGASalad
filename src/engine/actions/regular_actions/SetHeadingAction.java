package engine.actions.regular_actions;

import engine.Parameter;
import engine.actions.Action;

/**
 * Set the bearing of this entity to the specified angle.
 * 
 * @author nikita
 */
public class SetHeadingAction extends Action {

	public SetHeadingAction() {
		addParam(new Parameter(getResource("Heading"), double.class, 0.0));
	}

	@Override
	public void act() {
		System.out.println("before set heading action: "  + getEntity().getRotate());
		getEntity().setRotate((double) getParam(getResource("Heading")));
		getGameInfo().getGraphicsEngine().getView().getChildren().forEach(s -> System.out.print("ROTATE PROPERTY: " + s.rotateProperty().get()));
		System.out.println("CALLED: " + getEntity().getRotate());
	}
}
