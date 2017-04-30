package engine.actions.regular_actions;

import engine.entities.Entity;

public class EntitySpawnAtClickAction extends EntitySpawnAction {

	@Override
	protected void placeEntity(Entity existing, Entity newEntity) {
		newEntity.setX(getGameInfo().getObservableBundle().getInputObservable().getLastPressedCoordinates().getX()
				- newEntity.getWidth() / 2);
		newEntity.setY(getGameInfo().getObservableBundle().getInputObservable().getLastPressedCoordinates().getY()
				- newEntity.getHeight() / 2);
	}
}
