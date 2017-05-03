package engine.entities.entities;

import engine.entities.Entity;

/**
 * Entity that represents a character. Different from a block in that it can
 * have additional information, such as health or lives.
 * 
 * @author nikita
 */
public class CharacterEntity extends Entity {

	@Override
	protected void setupDefaultParameters() {
		defaultSetup();
		this.setName(getResource("Mario"));
		this.setImagePath(getClass().getClassLoader().getResource("resources/images/mario.png").toExternalForm());
		this.updateParam(getResource("Lives"), 5);
		this.setYAcceleration(Entity.YACCELERATION);
	}
}
