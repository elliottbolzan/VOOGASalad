package player.menus;

import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import polyglot.Polyglot;

public class ReplaceSaveMenu {

	private Polyglot polyglot;
	private ResourceBundle IOResources;
	private Stage stage;
	private ListView<String> saves;

	public ReplaceSaveMenu(Polyglot polyglot, ResourceBundle IOResources) {
		this.polyglot = polyglot;
		this.IOResources = IOResources;
		setupScene();
	}

	private void setupScene() {
		stage = new Stage();
		BorderPane root = new BorderPane();
		root.setPrefHeight(200);
		root.setPadding(new Insets(10));

		Label prompt = new Label();
		prompt.textProperty().bind(polyglot.get("SavePrompt"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(IOResources.getString("StylesheetPath"));
		saves = new ListView<>();
		saves.setPadding(new Insets(10, 0, 0, 0));

		root.setTop(prompt);
		BorderPane.setAlignment(prompt, Pos.CENTER);
		root.setCenter(saves);
		stage.setScene(scene);
	}

	public void display(EventHandler<MouseEvent> e) {
		for (int i = 0; i < 10; i++) {
			saves.getItems().add(polyglot.getOriginal(Integer.toString(i + 1)));
			saves.setOnMouseClicked(e);
		}
		stage.show();
	}

	public int getButtonID() {
		return saves.getSelectionModel().getSelectedIndex();
	}

	public void close() {
		stage.close();
	}
}
