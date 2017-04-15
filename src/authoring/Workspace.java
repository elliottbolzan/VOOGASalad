package authoring;

import java.io.File;
import java.util.ResourceBundle;

import authoring.canvas.LevelEditor;
import authoring.components.ComponentMaker;
import authoring.panel.Panel;
import authoring.views.View;
import engine.Entity;
import game_data.Game;
import game_data.GameData;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import player.BasicPlayer;

/**
 * @author Elliott Bolzan (modified by Mina Mungekar, Jimmy Shackford, Jesse Yue)
 *
 *         The container for the Game Authoring Environment. Displays a
 *         SplitPane, which contains the Panel and the Canvas. Serves as an
 *         intermediary between the default Entities, the Panel, and the Canvas.
 *
 */
public class Workspace extends View
{

	private ResourceBundle resources;
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
	 * @param resources
	 *            the ResourceBundle that pertains to this Workspace.
	 * @param path
	 *            the path of the Game to be loaded.
	 */
	public Workspace(ResourceBundle resources, String path)
	{
		super("Workspace");
		this.path = path;
		this.resources = resources;
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
	private void setup()
	{
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
		this.selectExistingLevel(levelEditor.getCurrentLevel().getLayerCount());
	}

	/**
	 * Save the Game to disk. A DirectoryChooser is presented to the user; the
	 * Game's construction is finalized; and a call to GameData is made to save
	 * the Game.
	 */
	public void save() {
		path = "";
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

	/**
	 * Test the Game that is currently being designed.
	 * 
	 * @param game the Game to test.
	 *            
	 */
	public void test(Game game) {
		new BasicPlayer(game, path);
	}
	
	/**
	 * 
	 * @returns if there is an existing path or not
	 */
	public boolean pathExists(){
		if(path.equals("")){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * @return the ResourceBundle for this View's descendants.
	 */
	public ResourceBundle getResources() {
		return resources;
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
	 * Show an error message to the user.
	 * 
	 * @param message
	 *            the message to be shown.
	 */
	public void showMessage(String message) {
		maker.makeAlert(AlertType.ERROR, "ErrorTitle", "ErrorHeader", message).showAndWait();
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
	public void selectExistingLevel(int newLevelNum) {
		panel.selectExistingLevelBox(newLevelNum);
	}

	/**
	 * When the user instructs the layer panel to delete a layer, the workspace
	 * alerts the levelEditor, telling it to delete a layer of its current
	 * level.
	 * 
	 * @param layer
	 *            the identifier of the layer to be deleted.
	 */
	public void deleteLayer(int layer)
	{
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

}