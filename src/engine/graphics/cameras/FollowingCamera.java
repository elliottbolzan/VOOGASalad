package engine.graphics.cameras;

import engine.Entity;
import javafx.scene.layout.Pane;

public class FollowingCamera extends Camera {

	private Entity leader;
	private Pane displayArea;
	
	public FollowingCamera(Entity entityToFollow, Pane displayArea) {
		super(0,0);
		this.leader = entityToFollow;
		this.displayArea = displayArea;
		this.update();
	}

	@Override
	public void update() {
		//TODO: adjust this to center the character on screen by subtracting width/2 and height/2
		this.setPosition(leader.getX() - displayArea.getWidth()/2, leader.getY() - displayArea.getHeight()/2);
	}
}
