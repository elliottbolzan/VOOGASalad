package engine.entities.entities;

import engine.entities.Entity;

public class BackgroundEntity extends Entity {

	public BackgroundEntity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setupDefaultParameters() {
		defaultSetup();
		this.setName("Background");
		this.setImagePath(getClass().getClassLoader().getResource("resources/images/background_cave.png").toExternalForm());
		this.setIsVisible(false);
	}

}
