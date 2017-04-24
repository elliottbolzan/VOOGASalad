package engine.entities;

import engine.Entity;
import engine.Parameter;

public class AchievementEntity extends Entity{
	
	public AchievementEntity(){
		addParam(new Parameter("Description", String.class, ""));
	}
	
	@Override 
	public void update(){
		
	}
}
