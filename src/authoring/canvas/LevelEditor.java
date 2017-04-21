package authoring.canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import authoring.Workspace;
import utils.views.View;
import engine.Entity;
import engine.game.Level;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;

/**
 * LevelEditor keeps track of multiple levels and assigns a LayerEditor to each
 * LevelEditor. The LevelEditor displays each Layer through a TabPane structure
 * where each Level has its own tab.
 * 
 * @author jimmy
 *
 */
public class LevelEditor extends View {

	private Workspace workspace;
	private TabPane tabPane;
	private LayerEditor currentLevel;
	private List<EntityView> copiedEntities;
	private List<LayerEditor> levels;
	private int levelCount;
	private HelpBar helpBar;

	/**
	 * Make a new LevelEditor
	 * 
	 * @param workspace
	 *            The workspace that the LevelEditor is currently in.
	 */
	public LevelEditor(Workspace workspace) {
		this.workspace = workspace;
		setup();
	}

	/**
	 * Gets the list of all the levels that this LevelEditor currently keeps
	 * contains. This method actually converts the List<Entities> from each
	 * LayerEditor into a Level object.
	 * 
	 * @return List of levels that this LevelEditor keeps track of.
	 */
	public List<Level> getLevels() {
		List<Level> currentLevels = new ArrayList<Level>();
		for (LayerEditor level : levels) {
			currentLevels.add(level.getLevel());
		}
		return currentLevels;
	}

	/**
	 * Sets the levels of this LevelEditor to those described in the given List
	 * of Levels.
	 * 
	 * @param levels
	 *            List of levels to load for this LevelEditor.
	 */
	public void loadGame(List<Level> levels) {
		setup();
		for (Level level : levels) {
			tabPane.getSelectionModel().select(0);
			currentLevel.loadLevel(level);
			if (levelCount < levels.size()) {
				tabPane.getTabs().add(tabPane.getTabs().size() - 1, newTab());
			}
		}
	}

	/**
	 * Update the Entities in each Level to match their default Entity.
	 * 
	 * @param entity
	 *            the default Entity to match.
	 */
	public void updateEntity(Entity entity) {
		for (LayerEditor layerEditor : levels) {
			layerEditor.updateEntity(entity);
		}
	}

	/**
	 * Initialize the LevelEditor.
	 */
	private void setup() {
		levelCount = 0;
		levels = new ArrayList<LayerEditor>();
		tabPane = new TabPane();
		copiedEntities = new ArrayList<EntityView>();
		tabPane.getTabs().add(newTab());
		tabPane.getTabs().add(makePlusTab());
		setCenter(tabPane);
		this.addKeyActions();
	}

	/**
	 * Make a new tab (level) for the LevelEditor.
	 * 
	 * @return Tab Tab representing the new level
	 */
	private Tab newTab() {
		Tab tab = new Tab();
		levelCount++;
		tab.setText(String.format("Level %d", levelCount));
		if (levelCount > 1) {
			LayerEditor oldLevel = currentLevel;
			currentLevel = oldLevel.clone();
		} else {
			currentLevel = new LayerEditor(workspace);
		}
		levels.add(currentLevel);
		tab.setContent(currentLevel);
		tab.setOnCloseRequest(e -> {
			closeRequest(e);
			levels.remove(currentLevel);
		});
		return tab;
	}

	private void addKeyActions() {
		tabPane.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.C) && e.isControlDown()) {
				copiedEntities.clear();
				for (Layer layer : currentLevel.getLayers()) {
					copiedEntities.addAll(layer.getSelectedEntities());
				}
			}
			if (e.getCode().equals(KeyCode.V) && e.isControlDown()) {
				for (Layer layer : currentLevel.getLayers()) {
					layer.getSelectedEntities().forEach(entity -> entity.setSelected(false));
				}
				for (EntityView entity : copiedEntities) {
					currentLevel.addEntity(entity.getEntity(), entity.getEntity().getX() + 25,
							entity.getEntity().getY() + 25, currentLevel.getCurrentLayer()).setSelected(true);
				}
			}
			if (e.getCode().equals(KeyCode.UP)) {
				for (Layer currLayer : currentLevel.getLayers()) {
					currLayer.getSelectedEntities().forEach(entity -> {
						entity.moveYGrid(-1);
					});
				}
				e.consume();
			}
			if (e.getCode().equals(KeyCode.DOWN)) {
				for (Layer currLayer : currentLevel.getLayers()) {
					currLayer.getSelectedEntities().forEach(entity -> {
						entity.moveYGrid(1);
					});
				}
				e.consume();
			}
			if (e.getCode().equals(KeyCode.RIGHT)) {
				for (Layer currLayer : currentLevel.getLayers()) {
					currLayer.getSelectedEntities().forEach(entity -> {
						entity.moveXGrid(1);
					});
				}
				e.consume();
			}
			if (e.getCode().equals(KeyCode.LEFT)) {
				for (Layer currLayer : currentLevel.getLayers()) {
					currLayer.getSelectedEntities().forEach(entity -> {
						entity.moveXGrid(-1);
					});
				}
				e.consume();
			}
		});
	}

	/**
	 * Make a close confirmation request. This is created whenever the user
	 * tries to exit out of a level.
	 * 
	 * @param e
	 *            Event that close confirmation request is attached to
	 */
	private void closeRequest(Event e) {
		Alert alert = workspace.getMaker().makeAlert(AlertType.CONFIRMATION, "ConfirmationTitle", "ConfirmationHeader",
				workspace.getPolyglot().get("ConfirmationContent"));
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() != ButtonType.OK) {
			e.consume();
		}
	}

	/**
	 * Gets the LayerEditor for the currently selected level
	 * 
	 * @return LayerEditor describing the currently selected level
	 */
	public LayerEditor getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * Make a plus tab which adds a new Level tab whenever it is pressed
	 * 
	 * @return Plus tab
	 */
	private Tab makePlusTab() {
		Tab plusTab = new Tab("+");
		plusTab.setClosable(false);
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
				if (newTab.getText().equals("+")) {
					tabPane.getTabs().add(tabPane.getTabs().size() - 1, newTab());
					workspace.selectExistingLevel(oldTab.getText(), newTab.getText());
					tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
					workspace.selectExistingLevel(newTab.getText(),
							tabPane.getSelectionModel().getSelectedItem().getText());
				} else if (!newTab.getText().equals("+") && !oldTab.getText().equals("+")) {
					currentLevel = (LayerEditor) tabPane.getSelectionModel().getSelectedItem().getContent();
					workspace.selectExistingLevel(oldTab.getText(), newTab.getText());
					currentLevel.select();
				}
			}

		});
		return plusTab;
	}

}