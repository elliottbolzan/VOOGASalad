package engine.actions.regular_actions;

import engine.actions.Action;
import engine.entities.entities.CharacterEntity;
import exceptions.GameObjectException;

/**
 * Allows the associated CharacterEntity to gain an additional life when the
 * act() method is called.
 * 
 * @author Kyle Finke
 *
 */
public class GainLifeAction extends Action {

	/**
	 * Add an additional life to the associated Entity if it is a
	 * CharacterEntity.
	 * 
	 * @throws GameObjectException
	 *             if associated Entity is not a CharacterEntity.
	 */
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
