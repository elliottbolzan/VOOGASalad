package engine.entities.entities;

import engine.entities.Entity;

/**
 * Entity that represents a character. Different from a block in that it can
 * have additional information, such as health or lives.
 * 
 * @author nikita
 * @author Matthew Barbano
 */
public class CharacterEntity extends Entity {

	@Override
	protected void setupDefaultParameters() {
		defaultSetup();
		this.setName("Mario");
		this.setImagePath(getClass().getClassLoader().getResource("resources/images/mario.png").toExternalForm());
		this.updateParam("Lives", 5);
		this.setYAcceleration(Entity.YACCELERATION);
	}
	
	/**
	 * 
	 * @return The number of lives that the CharacterEntity has
	 */
	@Override
	public int getLives() {
		return super.getLives();
	}
	
	/**
	 * Sets the number of lives for the CharacterEntity
	 * 
	 * @param lives
	 *            new number of lives for this CharacterEntity
	 */
	@Override
	public void setLives(int lives) {
		super.setLives(lives);
	}
}
