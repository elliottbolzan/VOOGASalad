package engine.graphics;

import javafx.scene.layout.BorderPane;
import player.menu.AbstractMenu;

/**
 * For displaying the level selection screen
 * 
 * @author Matthew Barbano
 *
 */
public class LevelSelectionMenu{
	private static final int COLUMNS = 2;
	private BorderPane displayArea;
	
	public LevelSelectionMenu(BorderPane displayArea){
		this.displayArea = displayArea;
	}

	public void draw() {
		//DECISION: Make my own, don't use Menu code; so can set with CSS, etc. (especially the one in the authoring environment) - perhaps allow authoring environment to choose the CSS!
	}
}
