package authoring.components;

import javafx.beans.binding.StringBinding;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * @author Elliott Bolzan
 *
 *         This class serves to display an HTML-formatted file.
 */
public class HTMLDisplay extends Stage {

	/**
	 * Creates an HTMLDisplay.
	 * @param filePath the path to the HTML file to be displayed.
	 * @param title the title of the HTMLDisplay.
	 */
	public HTMLDisplay(String filePath, StringBinding title) {
		WebView browser = new WebView();
		WebEngine webEngine = browser.getEngine();
		webEngine.load(getClass().getClassLoader().getResource(filePath).toExternalForm());
		Scene scene = new Scene(browser, 800, 600);
		titleProperty().bind(title);
		setScene(scene);
	}

}
