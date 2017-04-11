package player;


import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ReplaceSaveMenu {
	private ResourceBundle resources = ResourceBundle.getBundle("resources/Player");
	private Stage stage;
	private ListView<String> saves;
	
	public ReplaceSaveMenu(){
		setupScene();
	}

	private void setupScene(){
		stage = new Stage();
		BorderPane root = new BorderPane();
		Label prompt = new Label(resources.getString("SavePrompt"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(resources.getString("StylesheetPath"));
		saves = new ListView<>();

		
		root.setTop(prompt);
		root.setCenter(saves);
		stage.setScene(scene);
		
	}
	
	public void display(EventHandler<MouseEvent> e){
		for(int i = 0; i < 10; i++){
			saves.getItems().add(resources.getString(Integer.toString(i+1)));
			saves.setOnMouseClicked(e);
		}
		stage.show();
	}
	
	public int getButtonID(){
		return saves.getSelectionModel().getSelectedIndex();
	}
	
	public void close(){
		stage.close();
	}
}
