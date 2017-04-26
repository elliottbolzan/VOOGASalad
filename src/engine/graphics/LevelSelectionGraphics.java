package engine.graphics;

import engine.game.LevelManager;
import engine.game.gameloop.LevelSelectionStepStrategy;
import javafx.beans.binding.StringBinding;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
	private static final int LOCKED_LEVEL_OPACITY = 0;
	private static final int COLUMNS = 2;
	private static final int HORIZONTAL_GAP = 20;
	private static final int VERTICAL_GAP = HORIZONTAL_GAP;
	private static final Insets PADDING = new Insets(80, 20, 20, 20);
	private static final String LEVEL_TEXT_NAME = "LevelStringSelectionScreen";
	private static final String QUIT_TEXT_NAME = "QuitStringSelectionScreen";
	private static final String[] POSSIBLE_TILE_COLORS = {"orange", "yellow", "green", "blue"};
	private static final String QUIT_BUTTON_COLOR = "red";

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
	}

	private void displayButtons() {
		int levelNumber = 1;
		int firstOpenCellRow = -1, firstOpenCellColumn = -1;
		int row = 0;
		
		for(int column = 0; column < COLUMNS; column++){
			for(row = 0; row < levelManager.getLevels().size()/COLUMNS; row++){
				if(levelNumber <= levelManager.getLevels().size()){
					EventHandler<Event> handler;
					if(levelManager.getUnlockedLevelNumbers().contains(levelNumber)){
						final int copyOfLevelNumber = levelNumber; //Needed because if use inside lambda's inner class, must be final (bug fix citation: http://stackoverflow.com/questions/33799800/java-local-variable-mi-defined-in-an-enclosing-scope-must-be-final-or-effective)
						handler = e -> strategy.moveToLevelScreen(copyOfLevelNumber);
					}
					else{
						handler = e -> {/*Intentionally left blank.*/};
					}
					Tile tile = addTile((StringBinding) polyglot.get(LEVEL_TEXT_NAME, Case.TITLE).concat(" " + levelNumber), POSSIBLE_TILE_COLORS[levelNumber % POSSIBLE_TILE_COLORS.length], handler, row, column);
					if(!levelManager.getUnlockedLevelNumbers().contains(levelNumber)){
						tile.setOpacity(LOCKED_LEVEL_OPACITY);
					}
				}
				else{
					firstOpenCellRow = row;
					firstOpenCellColumn = column;
					break;
				}
				levelNumber++;
			}
		}
		
		if(firstOpenCellRow == -1 || firstOpenCellColumn == -1){
			addTile((StringBinding) polyglot.get(QUIT_TEXT_NAME, Case.TITLE), QUIT_BUTTON_COLOR, e -> strategy.quit(), row + 1, 0);
		}
		else{
			addTile((StringBinding) polyglot.get(QUIT_TEXT_NAME, Case.TITLE), QUIT_BUTTON_COLOR, e -> strategy.quit(), firstOpenCellRow, firstOpenCellColumn);
		}
	}
	
	private Tile addTile(StringBinding stringBinding, String colorName, EventHandler<Event> handler, int row, int column){
		Tile tile = new Tile(stringBinding, colorName, handler);
		pane.add(tile, column, row);
		return tile;
	}
}
