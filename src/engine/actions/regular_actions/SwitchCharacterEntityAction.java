package engine.actions.regular_actions;

import engine.Action;
import engine.Entity;
import engine.Parameter;
import engine.entities.CharacterEntity;
import game_data.Game;
import javafx.application.Platform;

/**
 * Switches the Entity currently associated with the Action with the Entity
 * associated with the Parameter "Character Entity". If there is no Entity
 * associated with Character Entity, then nothing changes.
 * 
 * @author Kyle Finke
 *
 */
public class SwitchCharacterEntityAction extends Action {

	public SwitchCharacterEntityAction() {
		addParam(new Parameter("Character Entity", String.class, ""));
	}

	@Override
	public void act() {
		Platform.runLater(new Runnable() {
			public void run() {
				CharacterEntity tempEntity = (CharacterEntity) getEntity().clone();
				Entity newEntity = null;
				for (Entity entity : getGameInfo().getLevelManager().getGame().getDefaults()) {
					if (((String) getParam("Character Entity")).equals(entity.getName())) {
						newEntity = entity.clone();
						break;
					}
				}
				System.out.println("Yes?");
				getEntity().setImagePath(newEntity.getImagePath());
				getEntity().setEvents(newEntity.getEvents());
//				System.out.println(getEntity().getEvents());
//				System.out.println(getEntity().getEvents().get(0).getActions());
//				tempEntity.setImagePath(newEntity.getImagePath());
//				tempEntity.setEvents(newEntity.getEvents());
//				getGameInfo().getLevelManager().getCurrentLevel().removeEntity(getEntity());
//				tempEntity.getGameInfo().getLevelManager().getCurrentLevel().addEntity(tempEntity);
//				tempEntity.getGameInfo().getGraphicsEngine().updateView();
				// getGameInfo().getLevelManager().getCurrentLevel().removeEntity(getEntity());
				// getGameInfo().getGraphicsEngine().updateView();
			}
		});
	}

}
