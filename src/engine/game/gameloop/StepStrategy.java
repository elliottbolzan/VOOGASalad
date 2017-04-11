package engine.game.gameloop;

import engine.GameInfo;
import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;
import javafx.scene.Scene;

public interface StepStrategy {
	/**
	 * Called from Screen's constructor. Safest to put preliminary setup code rather than
	 * subclass's constructor since do not know when it is instantiated, but more predictable
	 * when Screen's constructor is called. 
	 * @param info 
	 */
	public void setup(LevelManager levelManager, Scene gameScene, Screen screen, GraphicsEngine graphicsEngine, GameInfo info);
	
	/**
	 * Called from Screen's step() method.
	 */
	public void step();
}