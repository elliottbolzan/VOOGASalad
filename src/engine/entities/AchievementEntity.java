package engine.entities;

import engine.Entity;
import engine.Parameter;
import engine.events.additional_events.FinishAchievementEvent;
import javafx.beans.property.*;

public class AchievementEntity extends Entity{
	private DoubleProperty completed;
	private DoubleProperty total;

	@Override
	protected void setupDefaultParameters() {
		addParam(new Parameter("Description", String.class, ""));
		this.setImagePath(getClass().getClassLoader().getResource("resources/images/camera.png").toExternalForm());
		addAdditionalEventClass(FinishAchievementEvent.class);
		completed = new SimpleDoubleProperty();
		total = new SimpleDoubleProperty();
	}
	
	public DoubleProperty getCompleted(){		
		return completed;
	}
	
	public void setCompleted(double value){
		completed.set(value);
	}
	
	public DoubleProperty getTotal(){
		return total;
	}
	
	public void setTotal(double value){
		total.set(value);
	}

}
