package player.menu;

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
	private MediaManager loader;
	private Game game;
	private GridPane grid;

	public AbstractMenu(Stage stage, Game game, MediaManager loader, String title, Polyglot polyglot, ResourceBundle IOResources) {
		super(polyglot, IOResources);
		this.game = game;
		this.stage = stage;
		this.loader = loader;
		this.fontPath = IOResources.getString("FontPath");
		loadFont();
		setupView(title);
		addElements();
	}
	
	public abstract void addElements();
	
	protected MediaManager getLoader() {
		return loader;
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
	
	private void createBackButton(boolean backButton) {
		if (backButton) {
			Tile backTile = new Tile(getPolyglot().get("Back"), "gray", e -> back(stage));
			grid.add(backTile, getRow(grid.getChildren().size()), getColumn(grid.getChildren().size()));
		}
	}
	
	private int getRow(int i) {
		return i % 2;
	}
	
	private int getColumn(int i) {
		return (int) (i / 2);
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

	public void back(Stage stage) {
		new MainMenu(stage, game, loader, getPolyglot(), getResources());
	}
	
}
