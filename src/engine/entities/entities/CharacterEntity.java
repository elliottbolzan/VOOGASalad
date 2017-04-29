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
	private int initialLives;

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
	 * Stores the current number of lives as the initial number of lives. Called at the beginning of 
	 * the game loop when a game first starts up.
	 */
	public void initializeInitialLives(){
		initialLives = super.getLives();
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
	
	/**
	 * Gets the number of lives initially set by the Authoring Environment.
	 * @return
	 */
	public int getInitialLives(){
		return initialLives;
	}
	
	/**
	 * Resets number of lives to that initially specified by the Authoring Environment.
	 * @param initialLives
	 */
	public void setLivesToInitial(){
		super.setLives(initialLives);
	}
}
