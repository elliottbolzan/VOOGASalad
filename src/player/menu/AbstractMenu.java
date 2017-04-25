package player.menu;

import java.util.List;
import java.util.ResourceBundle;

import game_data.Game;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import player.MediaManager;
import player.PlayerView;
import polyglot.Polyglot;

/**
 * Abstract class of menu that defines all the common features of each menu
 * 
 * @author Jesse, Elliott Bolzan
 *
 */
public abstract class AbstractMenu extends PlayerView implements Menu {

	private Button back;
	private Stage stage;
	private String fontPath;
	private GridPane grid;
	
	private MediaManager mediaManager;
	private Game game;

	public AbstractMenu(Stage stage, Game game, MediaManager mediaManager, String title, Polyglot polyglot, ResourceBundle IOResources) {
		super(polyglot, IOResources);
		this.game = game;
		this.stage = stage;
		this.mediaManager = mediaManager;
		this.fontPath = IOResources.getString("FontPath");
		loadFont();
		setupView(title);
		addElements();
	}
	
	public abstract void addElements();
	
	protected MediaManager getMediaManager() {
		return mediaManager;
	}
	
	protected Stage getStage() {
		return stage;
	}

	protected Game getGame() {
		return game;
	}
	
	protected Button backButton() {
		return back;
	}

	protected void loadFont() {
		Font.loadFont(fontPath, 10);
	}
	
	public void addTiles(boolean backButton, Tile... tiles) {
		for (int i = 0; i < tiles.length; i++) {
			grid.add(tiles[i], getRow(i), getColumn(i));
		}
		createBackButton(backButton);
	}
	
	public void addSaveTiles(boolean backButton, List<Tile> tiles){
		Tile[] tileArray = new Tile[tiles.size()];
		tileArray = tiles.toArray(tileArray);
		grid.getChildren().clear();
		addTiles(backButton, tiles.toArray(tileArray));
	}
	
	private void createBackButton(boolean backButton) {
		if (backButton) {
			Tile backTile = this.makeBackButton();
			grid.add(backTile, getRow(grid.getChildren().size()), getColumn(grid.getChildren().size()));
		}
	}
	
	private int getRow(int i) {
		return i % 2;
	}
	
	private int getColumn(int i) {
		return (i / 2);
	}
	
	private void setupView(String title) {
		stage.getIcons().add(new Image(getResources().getString("IconPath")));
		stage.setMinWidth(420);
		stage.setMinHeight(600);
		setupGrid();
	}
	
	private void setupGrid() {
		grid = new GridPane();
	    grid.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		grid.setPadding(new Insets(6));
		grid.setHgap(6);
		grid.setVgap(6);
		ColumnConstraints first = new ColumnConstraints();
	    ColumnConstraints second = new ColumnConstraints();
	    first.setPercentWidth(50);
	    second.setPercentWidth(50);
	    grid.getColumnConstraints().addAll(first, second);
		setCenter(grid);
	}

	protected Tile makeBackButton() {
		return new Tile(getPolyglot().get("Back"), "gray", e -> back(stage));
	}
	
	public void back(Stage stage) {
		new MainMenu(stage, game, mediaManager, getPolyglot(), getResources());
	}
}
