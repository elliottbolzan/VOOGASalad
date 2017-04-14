package starter;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Elliott Bolzan
 *
 * This class is used to launch the program. 
 */
public class Main extends Application {
	
	/**
	 * Begins the launching process.
	 * @param args the arguments passed into main. 
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Launches the user interface.
	 * @param primaryStage the application's primary stage / window.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		new StartMenu(primaryStage);
	}

}