package engine.entities.entities;

import engine.entities.Entity;

/**
 * @author nikita 
 * 		   This entity represents different kinds of blocks in the game.
 *         A block could be moving (platform or enemy), static (wall) or other
 *         kind of entity. Not restricted to static "block".
 */
public class BlockEntity extends Entity {

	@Override
	protected void setupDefaultParameters() {
		defaultSetup();
		this.setName("Block");
		this.setImagePath(getClass().getClassLoader().getResource("resources/images/block.png").toExternalForm());
	}

}
