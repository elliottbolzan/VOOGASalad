package engine.actions.regular_actions;

import engine.Parameter;
import engine.actions.Action;
import engine.entities.Entity;

import java.util.Collection;

/**
 * This Action will be used for a following camera. Every frame it should set
 * the location of its Entity (the camera) based on a leader Entity (the
 * character).
 * 
 * @author Jay Doherty
 *
 */
public class FollowAction extends Action {

	public FollowAction() {
		this.addParam(new Parameter("Leader Entity", String.class, ""));
		this.addParam(new Parameter("Track Left", boolean.class, true));
		this.addParam(new Parameter("Track Right", boolean.class, true));
		this.addParam(new Parameter("Track Up", boolean.class, true));
		this.addParam(new Parameter("Track Down", boolean.class, true));
	}

	@Override
	public void act() {
		Collection<Entity> entities = this.getEntity().getGameInfo().getLevelManager().getCurrentLevel().getEntities();
		for (Entity entity : entities) {
			if (((String) getParam("Leader Entity")).equals(entity.getName())) {
				shiftHorizontally(entity);
				shiftVertically(entity);
			}
		}
	}

	private void shiftHorizontally(Entity other) {
		double thisEntityCenterX = this.getEntity().getX() + this.getEntity().getWidth() / 2;
		double otherEntityCenterX = other.getX() + other.getWidth() / 2;
		if (((boolean) getParam("Track Right") && thisEntityCenterX < otherEntityCenterX)
				|| ((boolean) getParam("Track Left") && thisEntityCenterX > otherEntityCenterX)) {
			this.getEntity().setX(other.getX() + (other.getWidth() / 2) - (this.getEntity().getWidth() / 2));
		}
	}

	private void shiftVertically(Entity other) {
		double thisEntityCenterY = this.getEntity().getY() + this.getEntity().getHeight() / 2;
		double otherEntityCenterY = other.getY() + other.getHeight() / 2;
		if (((boolean) getParam("Track Up") && thisEntityCenterY > otherEntityCenterY)
				|| ((boolean) getParam("Track Down") && thisEntityCenterY < otherEntityCenterY)) {
			this.getEntity().setY(other.getY() + (other.getHeight() / 2) - (this.getEntity().getHeight() / 2));
		}
	}
}
