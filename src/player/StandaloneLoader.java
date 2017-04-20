/**
 * 
 */
package player;

import java.io.File;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.stage.Stage;
import player.menu.MainMenu;
import polyglot.Polyglot;
import polyglot.PolyglotException;

/**
 * @author Elliott Bolzan
 *
 */
public class StandaloneLoader extends Application {

	private static final String KEY = "AIzaSyCOWQRgYSfbiNnOdIRPBcuY6iLTqwfmOc4";
	private static Polyglot polyglot;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		String pathToGame = new File("Game").getAbsolutePath();
		// Cannot work from a JAR yet. Game Data is not ready because they can't load files from a JAR.
		try {
			polyglot = new Polyglot(KEY, "resources/Strings");
		} catch (PolyglotException e) {
			System.out.println("The polyglot could not be initialized properly. Check your Internet.");
		}
		ResourceBundle IOResources = ResourceBundle.getBundle("resources/IO");
		new MainMenu(new Loader(pathToGame), polyglot, IOResources);
	}

}
