package authoring.command;

import authoring.canvas.EntityView;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

/**
 * 
 * @author jimmy
 *
 */
public class MoveCommand extends EntityCommand {

	double oldX;
	double oldY;
	double newX;
	double newY;

	public MoveCommand(EntityView entityView, MoveInfo moveInfo) {
		super(entityView);
		this.oldX = moveInfo.getOldX();
		this.oldY = moveInfo.getOldY();
		this.newX = moveInfo.getNewX();
		this.newY = moveInfo.getNewY();
	}

	@Override
	public void execute() {
		animate(newX - super.getEntityView().getTranslateX(), newY - super.getEntityView().getTranslateY());
	}

	@Override
	public void unexecute() {
		animate(oldX - newX, oldY - newY);
	}

	private void animate(double byX, double byY) {
		TranslateTransition translation = new TranslateTransition(Duration.millis(300), super.getEntityView());
		translation.setByX(byX);
		translation.setByY(byY);
		translation.play();
	}

}
