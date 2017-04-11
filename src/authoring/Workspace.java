package authoring;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import authoring.canvas.LevelEditor;
import authoring.components.ComponentMaker;
import authoring.panel.Panel;
import authoring.views.View;
import engine.Entity;
import engine.entities.CharacterEntity;
import engine.game.Level;
import game_data.Game;
import game_data.GameData;
import javafx.geometry.Insets;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SplitPane;
import javafx.stage.DirectoryChooser;

/**
 * @author Elliott Bolzan Modified by Mina Mungekar, Jimmy Shackford
 *
 */
public class Workspace extends View {

	private ResourceBundle resources;
	private ComponentMaker maker;
	private GameData data;

	private SplitPane pane;
	private LevelEditor levelEditor;
	private Panel panel;

	private Game game;
	private DefaultEntities defaults;

	/**
	 * 
	 */
	public Workspace(ResourceBundle resources, String path) {
		super("Workspace");
		this.resources = resources;
		setup();
		if (!path.equals("")) {
			load(path);
		}
	}

	public Game getGame() {
		return game;
	}

	/*public void updateEntity(Entity entity) {
		panel.updateEntity(entity);
	}*/

	/**
	 * Initializes the Workspace's components.
	 */
	private void setup() {
		game = new Game();
		data = new GameData();
		maker = new ComponentMaker(resources);
		defaults = new DefaultEntities(this);
		pane = new SplitPane();
		panel = new Panel(this, 0);
		levelEditor = new LevelEditor(this);
		pane.getItems().addAll(panel, levelEditor);
		pane.setDividerPositions(Double.parseDouble(resources.getString("DividerPosition")));
		setPadding(new Insets(Integer.parseInt(resources.getString("WorkSpaceInsets"))));
		setCenter(pane);
	}

	private void load(String path) {
		// game = data.loadGame(path);
		game = new Game();
		Level level = new Level();
		Level level2 = new Level();
		Entity one = new CharacterEntity();
		Entity two = new CharacterEntity();
		Entity three = new CharacterEntity();
		Entity four = new CharacterEntity();
		// one.setImagePath("resources/images/mario.png");
		one.xProperty().set(120);
		two.yProperty().set(200);
		three.xProperty().set(200);
		four.xProperty().set(300);

		three.setZ(1);
		four.setZ(10);
		level.addEntity(one);
		level.addEntity(two);
		level.addEntity(three);
		level.addEntity(four);
		level2.addEntity(one);
		List<Level> levels = new ArrayList<>();
		levels.add(level);
		levels.add(level2);
		game.setLevels(levels);

		levelEditor.loadGame(game.getLevels());
		defaults.setEntities(game.getDefaults());
		panel.getSettings().load(game);
	}

	public void save() {
		String path = "";
		String outputFolder = new File(resources.getString("GamesPath")).getAbsolutePath();
		DirectoryChooser chooser = maker.makeDirectoryChooser(outputFolder, "GameSaverTitle");
		File selectedDirectory = chooser.showDialog(getScene().getWindow());
		if (selectedDirectory != null) {
			path = selectedDirectory.getAbsolutePath();
		}
		game.setLevels(levelEditor.getLevels());
		if (!path.equals("")) {
			data.saveGame(game, path);
		}
	}

	public ResourceBundle getResources() {
		return resources;
	}

	public SplitPane getPane() {
		return pane;
	}

	public DefaultEntities getDefaults() {
		return defaults;
	}

	public Entity getSelectedEntity() {
		return defaults.getSelectedEntity();
	}

	public void showMessage(String message) {
		maker.makeAlert(AlertType.ERROR, "ErrorTitle", "ErrorHeader", message).showAndWait();
	}

	public List getEntities() {
		// return canvas's entities (i.e. canvas.getLevel())
		return new ArrayList<>();
	}

	public void setNewLayer(String newLayer) {
		panel.updateLayerPanel(newLayer);
	}

	public void addLayer() {
		levelEditor.getCurrentLevel().makeNewTab();
	}

	public void selectLayer(int arg2) {
		levelEditor.getCurrentLevel().selectLayer(arg2 - 1);
	}

	public void selectExistingLevel(int newLevelNum) {
		panel.selectExistingLevelBox(newLevelNum);
	}

	public void deleteLayer(int layer) {
		levelEditor.getCurrentLevel().deleteLayer(layer);
	}
}