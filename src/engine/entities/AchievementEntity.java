package engine.entities;

import engine.Entity;
import engine.Parameter;
import engine.events.additional_events.FinishAchievementEvent;

public class AchievementEntity extends Entity{

	@Override
	protected void setupDefaultParameters() {
		addParam(new Parameter("Description", String.class, ""));
		this.setImagePath(getClass().getClassLoader().getResource("resources/images/camera.png").toExternalForm());
		addAdditionalEventClass(FinishAchievementEvent.class);
	}
}
