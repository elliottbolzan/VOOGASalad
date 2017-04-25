package engine.actions.regular_actions;

import engine.Action;
import engine.Parameter;

/**
 * Updates the score of the current game by the amount associated with the
 * "Score Change" Parameter.
 * 
 * @author Kyle Finke
 *
 */
public class UpdateScoreAction extends Action {

	public UpdateScoreAction() {
		addParam(new Parameter("Score Change", Integer.class, 0));
	}

	@Override
	public void act() {
		getGameInfo().getScorebar().updateScore((Integer) getParam("Score Change"));
	}

}
