package engine.actions.regular_actions;

import java.util.Collection;

import engine.Action;
import engine.Entity;
import engine.Parameter;

/**
 * Used by the camera to follow another entity but only it's vertical position
 * @author Jesse
 *
 */
public class FollowVerticallyAction extends Action{
	
	public FollowVerticallyAction() {
		this.addParam(new Parameter("Leader Entity", String.class, ""));
	}

	@Override
	public void act() {
		Collection<Entity> entities = this.getEntity().getGameInfo().getLevelManager().getCurrentLevel().getEntities();
		for(Entity entity : entities) {
			if( ((String) getParam("Leader Entity")).equals(entity.getName()) ) {
				this.getEntity().setY(entity.getY() + (entity.getHeight()/2) - (this.getEntity().getHeight()/2));
			}
		}
	}

}
