package engine.entities;

import engine.Entity;

public class CharacterEntity extends Entity {
	//TODO currently lives are stored in Scorebar, which is necessary since it needs to save between
	//instantiations of entities. Need to remake it changeable by the GAE.
	
	public CharacterEntity() {
		//addParam(new Parameter("Lives", Integer.class, 5));
		this.setYAcceleration(Entity.ACCELERATION);
	}

	public int getLives() {
		return getGameInfo().getScorebar().getLives();
		//return (Integer) getParam("Lives");
	}

	public void setLives(int lives) {
		//updateParam("Lives", lives);
		getGameInfo().getScorebar().setLives(lives);
	}
}
