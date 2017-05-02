package engine.game.eventobserver;

import engine.graphics.GraphicsEngine;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

/**
 * Part of the Observable Design Pattern for detecting and responding to Events.
 * Handles both keyboard and mouse input.
 * 
 * @author Matthew Barbano
 *
 */
public class InputObservable extends EventObservable {
	private KeyCode lastPressedKey;
	private MouseButton lastPressedMouseButton;
	private Point2D lastPressedCoordinates;
	private Scene gameScene;
	private GraphicsEngine graphicsEngine;

	private boolean keyPressToProcess;
	private boolean keyReleaseToProcess;
	private boolean mouseClickToProcess;
	private boolean firstTime = true;
	
	/**
	 * Instatiates a new InputObservable by initializing observers to an empty ArrayList,
	 * the "lastPressed" fields to null, the "toProcess" fields to false, and the gameScene
	 * and graphicsEngine fields to those in the provided parameters.
	 * 
	 * @param gameScene
	 * @param graphicsEngine
	 */
	public InputObservable(Scene gameScene, GraphicsEngine graphicsEngine) {
		super();
		lastPressedKey = null;
		lastPressedMouseButton = null;
		lastPressedCoordinates = null;
		keyPressToProcess = false;
		keyReleaseToProcess = false;
		mouseClickToProcess = false;
		this.gameScene = gameScene;
		this.graphicsEngine = graphicsEngine;
	}
	
	/**
	 * @return keyPressToProcess
	 */
	public boolean isKeyPressToProcess() {
		return keyPressToProcess;
	}
	
	/**
	 * @return keyReleaseToProcess
	 */
	public boolean isKeyReleaseToProcess() {
		return keyReleaseToProcess;
	}
	
	/**
	 * @return mouseClickToProcess and not the first time clicked
	 */
	public boolean isMouseClickToProcess() {
		boolean temp = firstTime;
		firstTime = false;
		return !temp && mouseClickToProcess;
	}
	
	/**
	 * @return lastPressedKey
	 */
	public KeyCode getLastPressedKey() {
		return lastPressedKey;
	}
	
	/**
	 * @return lastPressedCoordinates, the coordinates relative to the graphicsEngine.getView()
	 * where the mouse was last clicked
	 */
	public Point2D getLastPressedCoordinates() {
		return lastPressedCoordinates;
	}
	
	/**
	 * @return lastPressedMouseButton
	 */
	public MouseButton getLastPressedMouseButton() {
		return lastPressedMouseButton;
	}
	
	/**
	 * Sets all the "toProcess" boolean variables to "state".
	 * @param state
	 */
	public void setInputToProcess(boolean state) {
		keyPressToProcess = state;
		keyReleaseToProcess = state;
		mouseClickToProcess = state;
	}
	
	/**
	 * Uses scene's setOnKeyPressed(), setOnKeyReleased(), and setOnMouseClicked()
	 * to initialize "lastPressed" and "toProcess" booleans to their appropriate values.
	 */
	public void setupInputListeners() {
		gameScene.setOnKeyPressed(event -> {
			lastPressedKey = event.getCode();
			keyPressToProcess = true;
		});
		gameScene.setOnKeyReleased(event -> {
			lastPressedKey = event.getCode();
			keyReleaseToProcess = true;
		});
		graphicsEngine.getView().setOnMouseClicked(e -> {
			lastPressedMouseButton = e.getButton();
			lastPressedCoordinates = new Point2D(e.getX(), e.getY());
			mouseClickToProcess = true;
		});
	}
	
	/**
	 * For InputObservable, does nothing (Null Object Design Pattern).
	 */
	public void updateObservers() {
	}

}
