package engine.actions.regular_actions;

import engine.entities.Entity;
import javafx.application.Platform;

public class EntitySpawnAtClickAction extends EntitySpawnAction {

	public EntitySpawnAtClickAction() {
		removeParam("Side");
		removeParam("Random Spawn");
	}

	@Override
	public void act() {
		System.out.println("SPAWNING");
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
		System.out.println("New entity: " + newEntity.getHeight() + " " + newEntity.getWidth());
		System.out.println("PLACED:" + " new x: " + newEntity.getX() + " new y: " + newEntity.getY());
	}
}
