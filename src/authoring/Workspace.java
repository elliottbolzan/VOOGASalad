package authoring;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import authoring.canvas.LevelEditor;
import authoring.components.ComponentMaker;
import authoring.components.ProgressDialog;
import authoring.panel.Panel;
import engine.Entity;
import game_data.Game;
import game_data.GameData;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import player.launcher.BasicPlayer;
import polyglot.Polyglot;
import utils.views.View;


/**
 * @author Elliott Bolzan (modified by Mina Mungekar, Jimmy Shackford, Jesse
 *         Yue)
 *
 *         The container for the Game Authoring Environment. Displays a
 *         SplitPane, which contains the Panel and the Canvas. Serves as an
 *         intermediary between the default Entities, the Panel, and the Canvas.
 *
 */
public class Workspace extends View {

	private Polyglot polyglot;
	private ResourceBundle IOResources;
	private ComponentMaker maker;
	private GameData data;

	private SplitPane pane;
	private LevelEditor levelEditor;
	private Panel panel;

	private Game game;
	private DefaultEntities defaults;
	private String path;

	/**
	 * Creates the Workspace.
	 * 
	 * @param IOResources
	 *            the ResourceBundle that pertains to this Workspace.
	 * @param path
	 *            the path of the Game to be loaded.
	 */
	public Workspace(String path, Polyglot polyglot, ResourceBundle IOResources) {
		this.path = path;
		this.polyglot = polyglot;
		this.IOResources = IOResources;
		setup();
		if (!path.equals("")) {
			load(path);
		}
	}

	/**
	 * @return the Workspace's current Game.
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Initializes the Workspace's components.
	 */
	private void setup() {
		game = new Game();
		data = new GameData();
		maker = new ComponentMaker(polyglot, IOResources.getString("StylesheetPath"));
		defaults = new DefaultEntities(this);
		pane = new SplitPane();
		panel = new Panel(this, 0);
		levelEditor = new LevelEditor(this);
		pane.getItems().addAll(panel, levelEditor);
		pane.setDividerPositions(0.25);
		setPadding(new Insets(5));
		setCenter(pane);
		dragToAddEntity();
	}

	private void dragToAddEntity() {
		panel.getEntityDisplay().getList().setOnDragDetected(e -> {
			Entity addedEntity = panel.getEntityDisplay().getList().getSelectionModel().getSelectedItem();
			Image image = new Image(addedEntity.getImagePath());
			panel.setCursor(new ImageCursor(image, 0, 0));
			levelEditor.setOnMouseEntered(e2 -> {
				levelEditor.getCurrentLevel().addEntity(addedEntity, e2);
				// levelEditor.getCurrentLevel().addEntity(addedEntity, e2.
				// e2.getSceneY(),
				// levelEditor.getCurrentLevel().getCurrentLayer());
				levelEditor.setOnMouseEntered(null);
				panel.setCursor(Cursor.DEFAULT);
			});
		});
	}

	private void load(String path) {
		game = data.loadGame(path);
		levelEditor.loadGame(game.getLevels());
		defaults.setEntities(game.getDefaults());
		panel.getSettings().load(game);
		this.selectLoadedLevel(levelEditor.getCurrentLevel().getLayerCount());
		//this.selectLoadedLevel(levelEditor.getCurrentLevel().getLayerNames());
	}

	/**
	 * Save the Game to disk. A DirectoryChooser is presented to the user; the
	 * Game's construction is finalized; and a call to GameData is made to save
	 * the Game.
	 */
	public void save() {
		TextInputDialog dialog = maker.makeTextInputDialog("SaveTitle", "SaveHeader", "SavePrompt", game.getName());
		Optional<String> result = dialog.showAndWait();
		result.ifPresent(name -> save(name));
	}
	
	private void save(String title) {
		game.setName(title);
		askForOutputPath();
		ProgressDialog dialog = new ProgressDialog(this);
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws InterruptedException {
				createGame();
				if (!path.equals("")) {
					data.saveGame(game, path);
				}
				return null;
			}
		};
		task.setOnSucceeded(event -> {
			dialog.getDialogStage().close();
		});
		Thread thread = new Thread(task);
		thread.start();
	}
	
	private void askForOutputPath() {
		path = "";
		String outputFolder = new File(IOResources.getString("GamesPath")).getAbsolutePath();
		DirectoryChooser chooser = maker.makeDirectoryChooser(outputFolder, "GameSaverTitle");
		File selectedDirectory = chooser.showDialog(getScene().getWindow());
		if (selectedDirectory != null) {
			path = selectedDirectory.getAbsolutePath();
		}
	}

	/**
	 * Test the Game that is currently being designed.
	 * 
	 * @param game
	 *            the Game to test.
	 * 
	 */
	public void test() {
		createGame();	
		Stage stage = new Stage();
		GameData loader = new GameData();
		new BasicPlayer(stage, loader.loadGame(path), polyglot, IOResources);
		stage.show();
	}
	
	private void createGame() {
		game.setLevels(levelEditor.getLevels());
	}

	/**
	 * 
	 * @returns if there is an existing path or not
	 */
	public boolean pathExists() {
		if (path.equals("")) {
			return false;
		} else {
			return true;
		}
	}
	
	public ComponentMaker getMaker() {
		return maker;
	}
	
	public Polyglot getPolyglot() {
		return polyglot;
	}

	/**
	 * @return the ResourceBundle for this View's descendants.
	 */
	public ResourceBundle getIOResources() {
		return IOResources;
	}

	/**
	 * @return the SplitPane governing this View.
	 */
	public SplitPane getPane() {
		return pane;
	}

	/**
	 * @return the default Entities the user has created.
	 */
	public DefaultEntities getDefaults() {
		return defaults;
	}

	/**
	 * @return the selected Entity.
	 */
	public Entity getSelectedEntity() {
		return defaults.getSelectedEntity();
	}

	/**
	 * addLayer is called from the panel whenever the user selects the option to
	 * add another layer. The workspace instructs the levelEditor to create
	 * another layer.
	 * 
	 */
	public void addLayer() {
		levelEditor.getCurrentLevel().newLayer();
	}

	/**
	 * When the user selects a new layer from the layer-selecting ComboBox, it
	 * alerts the workspace, which, in turn, instructs the LevelEditor to switch
	 * layers.
	 * 
	 * @param number
	 *            the layer's identifier.
	 */
	public void selectLayer(int number) {
		levelEditor.getCurrentLevel().selectLayer(number);
	}

	/**
	 * When a new level is selected by the user, the LevelEditor alerts the
	 * workspace, which, in turn, alerts the panel to the change being made. The
	 * combobox displaying layer names is subsequently updated.
	 * 
	 * @param newLevelNum
	 *            the number of the new level.
	 */
	public void selectExistingLevel(String oldLevel, String newLevel) {
		panel.selectExistingLevelBox(oldLevel, newLevel);
	}

	public void selectLoadedLevel(List<String> nameList) {
		panel.selectLoadedLevelBox(nameList);
	}
	public void selectLoadedLevel(int layerCount) {
		panel.selectLoadedLevelBox(layerCount);
	}

	/**
	 * When the user instructs the layer panel to delete a layer, the workspace
	 * alerts the levelEditor, telling it to delete a layer of its current
	 * level.
	 * 
	 * @param layer
	 *            the identifier of the layer to be deleted.
	 */
	public void deleteLayer(int layer) {
		levelEditor.getCurrentLevel().deleteLayer(layer);
	}

	/**
	 * Update Entities on the Canvas when their default was edited by the user.
	 * 
	 * @param entity
	 *            the Entity to replace the old Entities with.
	 */
	public void updateEntity(Entity entity) {
		levelEditor.updateEntity(entity);
	}
/**
 * Update layer name when user requests 
 * @param text
 */
	public void setLayerName(String text) {
		levelEditor.getCurrentLevel().setLayerName(text);
	}

}