package engine.graphics;

import engine.game.LevelManager;
import engine.game.gameloop.LevelSelectionStepStrategy;
import javafx.beans.binding.StringBinding;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import player.menu.AbstractMenu;
import player.menu.Tile;
import polyglot.Case;
import polyglot.Polyglot;

/**
 * For displaying the level selection screen. Some code taken from:
 * http://docs.oracle.com/javase/8/javafx/layout-tutorial/builtin_layouts.htm#JFXLY118
 * 
 * @author Matthew Barbano
 *
 */

public class LevelSelectionGraphics {
	private static final int COLUMNS = 2;
	private static final int HORIZONTAL_GAP = 20;
	private static final int VERTICAL_GAP = HORIZONTAL_GAP;
	private static final Insets PADDING = new Insets(80, 20, 20, 20);
	private static final String LEVEL_TEXT_NAME = "LevelStringSelectionScreen";
	private static final String LEVEL_LOCKED_TEXT_NAME = "LevelLockedStringSelectionScreen";
	private static final String[] POSSIBLE_TILE_COLORS = {"red", "orange", "yellow", "green", "blue"};

	private BorderPane displayArea;
	private LevelManager levelManager;
	private Polyglot polyglot;
	private LevelSelectionStepStrategy strategy;
	private GridPane pane;
	
	public LevelSelectionGraphics(BorderPane displayArea, LevelManager levelManager, Polyglot polyglot, LevelSelectionStepStrategy strategy) {
		this.displayArea = displayArea;
		this.levelManager = levelManager;
		this.polyglot = polyglot;
		this.strategy = strategy;
		this.pane = new GridPane();
	}

	public void draw() {
		setupPane();
		displayButtons();
		displayArea.setCenter(pane);
	}

	private void setupPane() {
		pane.setHgap(HORIZONTAL_GAP);
		pane.setVgap(VERTICAL_GAP);
		pane.setPadding(PADDING);
		//pane.getColumnConstraints().add(new ColumnConstraints(displayArea.getMaxWidth() / COLUMNS)); //TODO Resolve this bug - changing size tiles
	}

	private void displayButtons() {
		int levelNumber = 1;
		
		for(int column = 0; column < COLUMNS; column++){
			for(int row = 0; row < (int) Math.ceil(levelManager.getLevels().size()/(double) COLUMNS); row++){
				if(levelNumber <= levelManager.getLevels().size()){
					StringBinding text;
					EventHandler<Event> handler;
					if(levelManager.getUnlockedLevelNumbers().contains(levelNumber)){
						final int copyOfLevelNumber = levelNumber; //Needed because if use inside lambda's inner class, must be final (bug fix citation: http://stackoverflow.com/questions/33799800/java-local-variable-mi-defined-in-an-enclosing-scope-must-be-final-or-effective)
						handler = e -> strategy.moveToLevelScreen(copyOfLevelNumber);
						text = (StringBinding) polyglot.get(LEVEL_TEXT_NAME, Case.TITLE).concat(" " + levelNumber);
					}
					else{
						handler = e -> {};
						text = polyglot.get(LEVEL_LOCKED_TEXT_NAME, Case.UPPER);
					}
					addTile(text, POSSIBLE_TILE_COLORS[levelNumber % POSSIBLE_TILE_COLORS.length], handler, row, column);
				}
				levelNumber++;
			}
		}

	}
	
	private void addTile(StringBinding stringBinding, String colorName, EventHandler<Event> handler, int row, int column){
		Tile tile = new Tile(stringBinding, colorName, handler);
		pane.add(tile, column, row);
	}
}
