package engine.actions.regular_actions;

import engine.actions.Action;
import engine.entities.Entity;

public class FlipCameraSideAction extends Action {

	public FlipCameraSideAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void act() {
		Entity camera = getGameInfo().getGraphicsEngine().getCamera();
		if(getEntity().getX() > camera.getX()+camera.getWidth()){
			getEntity().setX(camera.getX());
		}else if (getEntity().getX()+getEntity().getWidth() < camera.getX()){
			getEntity().setX(camera.getX()+camera.getWidth()-getEntity().getWidth());
		}else if (getEntity().getY() > camera.getY()+camera.getHeight()){
			getEntity().setY(camera.getY());
		}else if (getEntity().getY()+getEntity().getHeight() < camera.getY()){
			getEntity().setY(camera.getY()+camera.getHeight()-getEntity().getHeight());;
		}
	}

}
