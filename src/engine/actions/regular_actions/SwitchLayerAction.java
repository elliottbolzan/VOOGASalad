package engine.actions.regular_actions;

import engine.Parameter;
import engine.actions.Action;
/**Switch the entity's layer.
 * 
 * @author nikita
 * */
public class SwitchLayerAction extends Action {

	public SwitchLayerAction() {
		addParam(new Parameter(getResource("NewLayer"), double.class, 0.0));
	}

	@Override
	public void act() {
		getEntity().setZ((double) getParam(getResource("NewLayer")));
		getGameInfo().getGraphicsEngine().updateView();
	}
}
