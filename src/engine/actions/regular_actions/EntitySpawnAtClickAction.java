package engine.actions.regular_actions;

import engine.entities.Entity;
import javafx.application.Platform;

public class EntitySpawnAtClickAction extends EntitySpawnAction {

	public EntitySpawnAtClickAction() {
		removeParam(getResource("Side"));
		removeParam(getResource("RandomSpawn"));
	}

	@Override
	public void act() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				spawn();
			}
		});
	}

	@Override
	protected void placeEntity(Entity existing, Entity newEntity) {
		newEntity.setX(getGameInfo().getObservableBundle().getInputObservable().getLastPressedCoordinates().getX()
				/*- newEntity.getWidth() / 2*/);
		newEntity.setY(getGameInfo().getObservableBundle().getInputObservable().getLastPressedCoordinates().getY()
				/*- newEntity.getHeight() / 2*/);
	}
}
