package engine.graphics;

import engine.game.LevelManager;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
	private static final Insets PADDING = new Insets(20);
	private static final String LEVEL_TEXT_NAME = "LevelStringSelectionScreen";
	private static final String QUIT_TEXT_NAME = "QuitStringSelectionScreen";

	private BorderPane displayArea;
	private LevelManager levelManager;
	private Polyglot polyglot;
	
	public LevelSelectionGraphics(BorderPane displayArea, LevelManager levelManager, Polyglot polyglot) {
		this.displayArea = displayArea;
		this.levelManager = levelManager;
		this.polyglot = polyglot;
	}

	public void draw() {
		GridPane pane = new GridPane();
		pane.setHgap(HORIZONTAL_GAP);
		pane.setVgap(VERTICAL_GAP);
		pane.setPadding(PADDING);
		
		int levelNumber = 1;
		int firstOpenCellRow = -1, firstOpenCellColumn = -1;
		int row = 0;
		
		for(int column = 0; column < COLUMNS; column++){
			for(row = 0; row < levelManager.getLevels().size()/COLUMNS; row++){
				if(levelNumber <= levelManager.getLevels().size()){
					Button button = new Button();  //TODO add an image here, maybe even make image change when hover over the button?
					button.textProperty().bind((StringBinding) polyglot.get(LEVEL_TEXT_NAME, Case.TITLE).concat(" " + levelNumber));
					pane.add(button, column, row);
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
			 //First on new row
			 Button button = new Button();
			 button.textProperty().bind(polyglot.get(QUIT_TEXT_NAME, Case.TITLE));
			 pane.add(button, 0, row + 1);
		}
		else{
			//In first open slot
			Button button = new Button();
			button.textProperty().bind(polyglot.get(QUIT_TEXT_NAME, Case.TITLE));
			pane.add(button, firstOpenCellColumn, firstOpenCellRow);
		}
		
		displayArea.setCenter(pane);
	}	
}
