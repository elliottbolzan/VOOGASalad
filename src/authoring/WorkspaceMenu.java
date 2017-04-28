package authoring;

import java.io.File;
import java.util.Optional;
import java.util.ResourceBundle;

import authoring.components.HTMLDisplay;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import polyglot.Case;
import utils.views.View;

/**
 * @author Elliott Bolzan
 *
 */
public class WorkspaceMenu extends View {

	private Workspace workspace;

	/**
	 * 
	 */
	public WorkspaceMenu(Workspace workspace) {
		this.workspace = workspace;
		setup();
	}

	private void setup() {
		Menu gameMenu = createGameMenu();
		Menu editMenu = createEditMenu();
		Menu settingsMenu = createSettingsMenu();
		Menu serverMenu = createServerMenu();
		Menu helpMenu = createHelpMenu();
		MenuBar menuBar = new MenuBar(gameMenu, editMenu, settingsMenu, serverMenu, helpMenu);
		VBox box = new VBox(menuBar);
		box.setPadding(new Insets(15, 0, 0, 0));
		setCenter(box);
	}

	/**
	 * @return
	 */
	private Menu createHelpMenu() {
		Menu helpMenu = workspace.getMaker().makeMenu("HelpTitle");
		helpMenu.getItems().addAll(workspace.getMaker().makeMenuItem(
				workspace.getPolyglot().get("KeyCombinations", Case.TITLE), "Ctrl+H", e -> showKeyCombinations()),
				workspace.getMaker().makeMenuItem(
						workspace.getPolyglot().get("AuthoringTour", Case.TITLE), "Ctrl+G", e -> initTutorial())
				);
		return helpMenu;
	}

	/**
	 * @return
	 */
	private Menu createSettingsMenu() {
		Menu settingsMenu = workspace.getMaker().makeMenu("SettingsTitle");
		settingsMenu.getItems().add(workspace.getMaker()
				.makeMenuItem(workspace.getPolyglot().get("MusicSelect", Case.TITLE), "Ctrl+M", e -> chooseSong()));
		return settingsMenu;
	}

	private Menu createEditMenu() {
		Menu editMenu = workspace.getMaker().makeMenu("EditTitle");
		editMenu.getItems()
				.addAll(workspace.getMaker().makeMenuItem(workspace.getPolyglot().get("Copy", Case.TITLE), "Ctrl+C",
						e -> workspace.getLevelEditor().copy()),
						workspace.getMaker().makeMenuItem(workspace.getPolyglot().get("Paste", Case.TITLE), "Ctrl+V",
								e -> workspace.getLevelEditor().paste()),
						workspace.getMaker().makeMenuItem(workspace.getPolyglot().get("SelectAll", Case.TITLE),
								"Ctrl+A", e -> workspace.getLevelEditor().getCurrentLevel().selectAll()));
		return editMenu;
	}

	/**
	 * @return
	 */
	private Menu createGameMenu() {
		Menu gameMenu = workspace.getMaker().makeMenu("GameMenu");
		gameMenu.getItems()
				.addAll(workspace.getMaker().makeMenuItem(workspace.getPolyglot().get("Save", Case.TITLE), "Ctrl+S",
						e -> workspace.save()),
						workspace.getMaker().makeMenuItem(workspace.getPolyglot().get("TestMenu", Case.TITLE), "Ctrl+T",
								e -> workspace.test()));
		return gameMenu;
	}

	private Menu createServerMenu() {
		Menu serverMenu = workspace.getMaker().makeMenu("ServerMenu");
		MenuItem IPItem = workspace.getMaker().makeMenuItem(workspace.getPolyglot().get("IPItem", Case.TITLE),
				"Ctrl+I", e -> workspace.getNetworking().showIP());
		MenuItem startItem = workspace.getMaker().makeMenuItem(
				workspace.getPolyglot().get("StartServerItem", Case.TITLE), "Ctrl+Shift+S",
				e -> workspace.getNetworking().start());
		MenuItem joinItem = workspace.getMaker().makeMenuItem(workspace.getPolyglot().get("JoinClientItem", Case.TITLE),
				"Ctrl+J", e -> workspace.getNetworking().join());
		serverMenu.getItems().addAll(IPItem, startItem, joinItem);
		return serverMenu;
	}

	private void chooseSong() {
		String directory = System.getProperty("user.dir") + workspace.getIOResources().getString("DefaultDirectory");
		FileChooser chooser = workspace.getMaker().makeFileChooser(directory,
				workspace.getPolyglot().get("MusicChooserTitle").get(),
				workspace.getIOResources().getString("MusicChooserExtensions"));
		File selectedFile = chooser.showOpenDialog(getScene().getWindow());
		if (selectedFile != null) {
			workspace.getGame().setSongPath(selectedFile.getAbsolutePath());
		}
	}

	private void showKeyCombinations() {
		HTMLDisplay display = new HTMLDisplay(workspace.getIOResources().getString("HelpPath"),
				workspace.getPolyglot().get("KeyCombinations"));
		display.show();
	}
	
	private void initTutorial(){
		new AuthoringTutorial(workspace.getPolyglot());
	}

}
