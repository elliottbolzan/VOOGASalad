package authoring.canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import authoring.Workspace;
import authoring.components.ComponentMaker;
import authoring.views.View;
import engine.game.Level;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * 
 * @author jimmy
 *
 */
public class LevelEditor extends View {

	Workspace workspace;
	TabPane tabPane;
	LayerEditor currentLevel;
	List<LayerEditor> levels;
	int levelCount;
	HelpBar helpBar;

	public LevelEditor(Workspace workspace) {
		super("");
		this.workspace = workspace;
		setup();
	}

	public List<Level> getLevels() {
		List<Level> currentLevels = new ArrayList<Level>();
		for (LayerEditor level : levels) {
			currentLevels.add(level.getLevel());
		}
		return currentLevels;
	}

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

	private void setup() {
		levelCount = 0;
		levels = new ArrayList<LayerEditor>();
		tabPane = new TabPane();
		tabPane.getTabs().add(newTab());
		tabPane.getTabs().add(makePlusTab());
		setCenter(tabPane);
		this.addToolbar();
	}

	private Tab newTab() {
		System.out.println(levelCount);
		Tab tab = new Tab();
		levelCount++;
		tab.setText(String.format("Level %d", levelCount));
		currentLevel = new LayerEditor(workspace);
		levels.add(currentLevel);
		tab.setContent(currentLevel);
		tab.setOnCloseRequest(e -> {
			closeRequest(e);
			levels.remove(currentLevel);
		});
		return tab;
	}

	private void closeRequest(Event e) {
		ComponentMaker maker = new ComponentMaker(workspace.getResources());
		String message = workspace.getResources().getString("ConfirmationContent");
		Alert alert = maker.makeAlert(AlertType.CONFIRMATION, "ConfirmationTitle", "ConfirmationHeader", message);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() != ButtonType.OK) {
			e.consume();
		}
	}

	public LayerEditor getCurrentLevel() {
		return currentLevel;
	}

	private Tab makePlusTab() {
		Tab plusTab = new Tab("+");
		plusTab.setClosable(false);
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
				if (newTab.getText().equals("+")) {
					tabPane.getTabs().add(tabPane.getTabs().size() - 1, newTab());
					tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
					currentLevel = (LayerEditor) tabPane.getSelectionModel().getSelectedItem().getContent();
					workspace.selectExistingLevel(currentLevel.getLayerCount());
				} else if (!newTab.getText().equals("+") && !oldTab.getText().equals("+")) {
					currentLevel = (LayerEditor) tabPane.getSelectionModel().getSelectedItem().getContent();
					workspace.selectExistingLevel(currentLevel.getLayerCount());
					currentLevel.select();
				}
			}

		});
		return plusTab;
	}

	private void addToolbar() {
		helpBar = new HelpBar(workspace);
		setBottom(helpBar);
	}

}
