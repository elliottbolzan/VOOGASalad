package engine;

import engine.entities.EntityInterface;

public interface GameInterface {
	//external
	void addLevel(LevelInterface level); //needed for authoring
	LevelInterface getCurrentLevel(); //needed for gameplay
	EntityInterface getPowerup();
}
