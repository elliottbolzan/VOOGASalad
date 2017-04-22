package engine.actions;

import engine.Action;
import engine.entities.CharacterEntity;
import exceptions.GameObjectException;

public class GainLifeAction extends Action {

	public GainLifeAction() {
	}

	@Override
	public void act() {
		try {
			CharacterEntity character = ((CharacterEntity) getEntity());
			character.setLives(character.getLives() + 1);
		} catch (Exception e) {
			throw new GameObjectException(getResource("NotCharacterEntity"));
		}
	}

}
