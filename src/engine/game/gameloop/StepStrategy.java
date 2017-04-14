package engine.game.gameloop;

import engine.GameInfo;
import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;
import javafx.scene.Scene;

/**
 * The interface for the Strategy Design Pattern whose subclasses can be substituted for 
 * different implementations of Screen's step().
 * @author Matthew Barbano
 *
 */

public interface StepStrategy {
	/**
	 * Called from Screen's constructor. Safest to put preliminary setup code rather than
	 * subclass's constructor since do not know when it is instantiated, but more predictable
	 * when Screen's constructor is called. 
	 * @param info 
	 */
	public void setup(LevelManager levelManager, Scene gameScene, Screen screen, GraphicsEngine graphicsEngine, GameInfo info);
	
	/**
	 * Called from Screen's step() method on every iteration of the Timeline.
	 */
	public void step();
}