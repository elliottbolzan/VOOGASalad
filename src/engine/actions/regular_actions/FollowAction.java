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
		this.addParam(new Parameter(getResource("LeaderEntity"), String.class, ""));
		this.addParam(new Parameter(getResource("TrackLeft"), boolean.class, true));
		this.addParam(new Parameter(getResource("TrackRight"), boolean.class, true));
		this.addParam(new Parameter(getResource("TrackUp"), boolean.class, true));
		this.addParam(new Parameter(getResource("TrackDown"), boolean.class, true));
	}

	@Override
	public void act() {
		Collection<Entity> entities = this.getEntity().getGameInfo().getLevelManager().getCurrentLevel().getEntities();
		for (Entity entity : entities) {
			if (((String) getParam(getResource("LeaderEntity"))).equals(entity.getName())) {
				shiftHorizontally(entity);
				shiftVertically(entity);
			}
		}
	}

	private void shiftHorizontally(Entity other) {
		double thisEntityCenterX = this.getEntity().getX() + this.getEntity().getWidth() / 2;
		double otherEntityCenterX = other.getX() + other.getWidth() / 2;
		if (((boolean) getParam(getResource("TrackRight")) && thisEntityCenterX < otherEntityCenterX)
				|| ((boolean) getParam(getResource("TrackLeft")) && thisEntityCenterX > otherEntityCenterX)) {
			this.getEntity().setX(otherEntityCenterX - (this.getEntity().getWidth() / 2)+300);
		}
	}

	private void shiftVertically(Entity other) {
		double thisEntityCenterY = this.getEntity().getY() + this.getEntity().getHeight() / 2;
		double otherEntityCenterY = other.getY() + other.getHeight() / 2;
		if (((boolean) getParam(getResource("TrackUp")) && thisEntityCenterY > otherEntityCenterY)
				|| ((boolean) getParam(getResource("TrackDown")) && thisEntityCenterY < otherEntityCenterY)) {
			this.getEntity().setY(otherEntityCenterY - (this.getEntity().getHeight() / 2)-150);
		}
	}
}
