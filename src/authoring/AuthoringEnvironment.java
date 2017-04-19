package authoring;

import java.util.ResourceBundle;

import javafx.scene.Scene;
import javafx.stage.Stage;
import polyglot.Polyglot;

/**
 * @author Elliott Bolzan
 *
 *         The most high-level class in the Authoring Environment. Creates the
 *         Stage for the Environment, and loads the Workspace into it.
 */
public class AuthoringEnvironment {

	private ResourceBundle IOResources;
	private Polyglot polyglot;
	private String gamePath;

	/**
	 * Creates an AuthoringEnvironment to edit an existing Game.
	 * 
	 * @param gamePath
	 *            the path of the Game to be loaded.
	 */
	public AuthoringEnvironment(String gamePath, Polyglot polyglot, ResourceBundle IOResources) {
		this.gamePath = gamePath;
		this.polyglot = polyglot;
		this.IOResources = IOResources;
		setupStage();
	}

	/**
	 * Creates a new AuthoringEnvironment.
	 */
	public AuthoringEnvironment(Polyglot polyglot, ResourceBundle IOResources) {
		this("", polyglot, IOResources);
	}

	private void setupStage() {
		Stage stage = new Stage();
		stage.titleProperty().bind(polyglot.get("AuthoringEnvironmentTitle"));
		stage.setMinWidth(600);
		stage.setMinHeight(300);
		stage.setScene(createScene());
		stage.show();
	}

	private Scene createScene() {
		Scene scene = new Scene(new Workspace(gamePath, polyglot, IOResources), 1000, 600);
		scene.getStylesheets().add(IOResources.getString("StylesheetPath"));
		return scene;
	}

}
