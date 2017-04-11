package player;

import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoadMenu extends AbstractMenu{
		
	private ResourceBundle resources = ResourceBundle.getBundle("resources/Player");
	private ObservableList<String> saveStates;
	private VBox saveStateButtons;
	
	public LoadMenu(Stage stage, String gameFolderPath){
		super(stage, gameFolderPath);
		saveStates = FXCollections.observableArrayList();
		setupScene(stage, gameFolderPath);
	}
	
	private void loadGame(Stage stage, String gameFolderPath) {
		new Player(gameFolderPath, saveStates);
	}
	
	private void setupScene(Stage stage, String gameFolderPath){
		
		VBox container = new VBox(20);
		saveStateButtons = new VBox(8);

		Button newGameButton = this.getFactory().makeButton("StartButton", e -> this.loadGame(stage, gameFolderPath), false);
		
		saveStates.addListener(new ListChangeListener<String>(){

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
				Button save = getFactory().makeButton(Integer.toString(saveStates.size()), e -> loadGame(stage, saveStates.get(saveStates.size()-1)), false);
				saveStateButtons.getChildren().add(save);			
			}
		});		
		
		container.getChildren().addAll(newGameButton, saveStateButtons);
		this.getRoot().setCenter(container);
	}
	
}
