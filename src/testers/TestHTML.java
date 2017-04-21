package testers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.web.*;
import javafx.stage.Stage;

public class TestHTML extends Application {
	private Stage window;
	@Override
	public void start(Stage arg0) throws Exception {
		window = arg0;
		VBox box = new VBox(10);
		Scene scene = new Scene(box);
		window.setScene(scene);
		
		HTMLEditor editor = new HTMLEditor();
		WebView browser = new WebView();
		WebEngine engine = browser.getEngine();
		Button load = new Button("Load");
		load.setOnAction(e -> {
			engine.loadContent(editor.getHtmlText());
			System.out.println(editor.getHtmlText());
		});
		box.getChildren().addAll(editor, load, browser);
		
		window.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
