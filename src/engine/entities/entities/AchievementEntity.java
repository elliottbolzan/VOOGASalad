package engine.entities.entities;

import engine.Parameter;
import engine.entities.Entity;
import engine.events.Event;
import engine.events.additional_events.FinishAchievementEvent;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Entity for achievements. Properties of achievements are very similar to those
 * of entities, so achievements were made to be a type of entity. Events owned
 * by this achievement should include finish achievement event. Other events
 * shouldn't have any actions associated with them.
 * 
 * @author nikita
 */
public class AchievementEntity extends Entity {

	@Override
	protected void setupDefaultParameters() {
		addParam(new Parameter("Description", String.class, ""));
		this.setImagePath(getClass().getClassLoader().getResource("resources/images/camera.png").toExternalForm());
		addAdditionalEventClass(FinishAchievementEvent.class);
	}

	@Override
	protected void move() {
	}

	/**
	 * Get percentage of progress made towards completing this achievement.
	 * 
	 * @return percentage of progress made towards completing this achievement
	 */
	public SimpleDoubleProperty getPercentCompleted() {
		double completed = 0, total = 0;
		for (Event event : getEvents()) {
			if (!(event instanceof FinishAchievementEvent)) {
				total += (int) event.getParam("How often to trigger");
				completed += event.getNumberTimesTriggered().get() >= (int) event.getParam("How often to trigger")
						? event.getNumberTimesTriggered().get() : (int) event.getParam("How often to trigger");
			}
		}
		return new SimpleDoubleProperty(completed / total);
	}
}
