package engine.actions;

import engine.Action;
import engine.Parameter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import poster.FacebookPoster;
import poster.FacebookResponse;

/**
 * @author nikita. Action that posts on Facebook. Uses the FacebookPoster
 *         utility submitted by myself to achieve the task.
 */
public class FacebookPostMessageAction extends Action {
	private FacebookPoster poster;

	public FacebookPostMessageAction() {
		addParam(new Parameter("Message", String.class, ""));
	}

	/**
	 * Post a message on Facebook. Show an alert showing success or failure of
	 * the post.
	 */
	@Override
	public void act() {
		if (poster == null)
			poster = new FacebookPoster(getNotTranslatedResource("APP_ID"), getNotTranslatedResource("SECRET_KEY"));
		getGameInfo().getTimelineManipulator().pause();
		poster.post((String) getParam("Message"), new FacebookResponse() {
			@Override
			public void doResponse(boolean condition) {
				Alert alert = new Alert(AlertType.INFORMATION,
						condition ? getResource("FacebookSuccessString") : getResource("FacebookFailString"));
				alert.setOnHidden(e -> getGameInfo().getTimelineManipulator().start());
				alert.show();
			}
		});
	}
}
