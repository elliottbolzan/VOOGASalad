package engine.graphics.cameras;

/**
 * This class keeps track of where in the game the Player should be displaying. Cameras get updated
 * every frame, and different Cameras can move their position in different ways (e.g. you can have
 * a continuously scrolling camera or one that follows the player around).
 * 
 * @author Jay Doherty
 */
public abstract class Camera {
	private double positionX;
	private double positionY;
	
	public Camera(double initialX, double initialY) {
		positionX = initialX;
		positionY = initialY;
	}
	
	public abstract void update();
	
	public void setPosition(double positionX, double positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
	}

	public double getX() {
		return positionX;
	}

	public double getY() {
		return positionY;
	}
}
