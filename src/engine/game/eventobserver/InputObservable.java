package engine.game.eventobserver;

import engine.graphics.GraphicsEngine;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

/**
 * Part of the Observable Design Pattern for detecting and responding to Events.
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

	// For Nikita to call in InputEvent's act()
	public boolean isKeyPressToProcess() {
		return keyPressToProcess;
	}

	public boolean isKeyReleaseToProcess() {
		return keyReleaseToProcess;
	}

	public boolean isMouseClickToProcess() {
		boolean temp = firstTime;
		firstTime = false;
		return !temp && mouseClickToProcess;
	}

	// For Nikita to call in InputEvent's act() - for key input
	public KeyCode getLastPressedKey() {
		return lastPressedKey;
	}

	// For Nikita to call in InputEvent's act() - for mouse input
	public Point2D getLastPressedCoordinates() {
		return lastPressedCoordinates;
	}

	// For Nikita to call in InputEvent's act() - for mouse input - see JavaFX's
	// MouseButton documentation for how to use
	public MouseButton getLastPressedMouseButton() {
		return lastPressedMouseButton;
	}

	// Matthew calls this from game loop (at end, AFTER update Entities/act
	// Events)
	public void setInputToProcess(boolean state) {
		keyPressToProcess = state;
		keyReleaseToProcess = state;
		mouseClickToProcess = state;
	}

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

	public void updateObservers() {
		// Intentionally left blank.
	}

}
