package engine.actions.regular_actions;

import engine.Action;
import engine.Entity;
import engine.Event;
import engine.Parameter;
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
				System.out.println(getEntity());
				Entity newEntity = null;
				for (Entity entity : getGameInfo().getLevelManager().getGame().getDefaults()) {
					if (((String) getParam("Character Entity")).equals(entity.getName())) {
						newEntity = entity.clone();
						System.out.println(newEntity);
						break;
					}
				}
				getEntity().setImagePath(newEntity.getImagePath());
				for (Event e : newEntity.getEvents()) {
					e.setGameInfo(getGameInfo());
					for (Action a : e.getActions()) {
						a.setGameInfo(getGameInfo());
					}
				}
				getEntity().setEvents(newEntity.getEvents());
				System.out.println(getEntity());
				System.out.println("----------------------------------------");
			}
		});
	}

}
