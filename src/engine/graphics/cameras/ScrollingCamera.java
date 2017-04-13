package engine.graphics.cameras;

/**
 * An implementation of Camera that continuously scrolls at a fixed rate in a given direction.
 * 
 * @author Jay Doherty
 *
 */
public class ScrollingCamera extends Camera {
	private double scrollSpeedX;
	private double scrollSpeedY;
	
	public ScrollingCamera(double speedX, double speedY) {
		super(0,0);
		scrollSpeedX = speedX;
		scrollSpeedY = speedY;
	}

	@Override
	public void update() {
		this.setPosition(this.getX() + scrollSpeedX, this.getY() + scrollSpeedY);
	}
}
