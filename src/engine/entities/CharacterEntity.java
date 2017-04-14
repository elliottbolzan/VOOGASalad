package engine.entities;

import engine.Entity;
import engine.Parameter;

/**
 * @author nikita Entity that represents a character. Different from a block in
 *         that it can have additional information, such as health or lives.
 */
public class CharacterEntity extends Entity {
	// TODO currently lives are stored in Scorebar, which is necessary since it
	// needs to save between
	// instantiations of entities. Need to remake it changeable by the GAE.

	public CharacterEntity() {
		addParam(new Parameter("Lives", Integer.class, 5));
		this.setYAcceleration(Entity.YACCELERATION);
	}

	/**
	 * 
	 * @return The number of lives that the CharacterEntity has
	 */
	public int getLives() {
		return getGameInfo().getScorebar().getLives();
		// return (Integer) getParam("Lives");
	}

	/**
	 * Sets the number of lives for the CharacterEntity
	 * 
	 * @param lives
	 *            new number of lives for this CharacterEntity
	 */
	public void setLives(int lives) {
		updateParam("Lives", lives);
		getGameInfo().getScorebar().setLives(lives);
	}
}
