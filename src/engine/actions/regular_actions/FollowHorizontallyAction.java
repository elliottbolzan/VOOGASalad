package engine.actions.regular_actions;

import java.util.Collection;

import engine.Action;
import engine.Entity;
import engine.Parameter;

/**
 * Used by the camera to follow another entity but only in it's horizontal position
 * @author Jesse
 *
 */
public class FollowHorizontallyAction extends Action{
	public FollowHorizontallyAction() {
		this.addParam(new Parameter("Leader Entity", String.class, ""));
	}

	@Override
	public void act() {
		Collection<Entity> entities = this.getEntity().getGameInfo().getLevelManager().getCurrentLevel().getEntities();
		for(Entity entity : entities) {
			if( ((String) getParam("Leader Entity")).equals(entity.getName()) ) {
				this.getEntity().setX(entity.getX() + (entity.getWidth()/2) - (this.getEntity().getWidth()/2));
			}
		}
	}

}
