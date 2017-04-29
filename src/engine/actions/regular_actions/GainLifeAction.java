package engine.actions.regular_actions;

import engine.actions.Action;

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
	 */
	@Override
	public void act() {
		getGameInfo().getScorebar().setLives(getGameInfo().getScorebar().getLives() + 1);
	}

}
