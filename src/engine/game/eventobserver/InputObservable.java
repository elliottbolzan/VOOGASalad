package engine.game.eventobserver;

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

	private boolean keyPressToProcess;
	private boolean keyReleaseToProcess;
	private boolean mouseClickToProcess;

	public InputObservable(Scene gameScene) {
		super();
		lastPressedKey = null;
		lastPressedMouseButton = null;
		lastPressedCoordinates = null;
		keyPressToProcess = false;
		keyReleaseToProcess = false;
		mouseClickToProcess = false;
		this.gameScene = gameScene;
	}

	// For Nikita to call in InputEvent's act()
	public boolean isKeyPressToProcess() {
		return keyPressToProcess;
	}

	public boolean isKeyReleaseToProcess() {
		return keyReleaseToProcess;
	}

	public boolean isMouseClickToProcess() {
		return mouseClickToProcess;
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
		gameScene.setOnMouseClicked(event -> {
			lastPressedMouseButton = event.getButton();
			System.out.println("X: " + event.getX() + " , Y: " + event.getY());
			System.out.println("Scene X: " + event.getSceneX() + " , Scene Y: " + event.getSceneY());
			System.out.println();
			lastPressedCoordinates = new Point2D(event.getX(), event.getY());
			mouseClickToProcess = true;
		});
	}

	public void updateObservers() {
		// Intentionally left blank.
	}
}
