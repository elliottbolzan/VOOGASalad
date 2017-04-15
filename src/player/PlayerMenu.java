package player;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PlayerMenu extends AbstractMenu{
	private String gameFolderPath;

	public PlayerMenu(Stage stage, String gameFolderPath) {
		super(stage, gameFolderPath, "PlayerTitle");
		this.gameFolderPath = gameFolderPath;
		
		setupScene();
		stage.setScene(display());	
		setTitle(getLoader().loadGame().getName());
		backButton().setOpacity(0.0);
		stage.show();
	}
	
	public PlayerMenu(String gameFolderPath){
		this(new Stage(), gameFolderPath);
	}
	
	private void setupScene(){
		VBox menu = new VBox(8);

		menu.getChildren().addAll(this.getFactory().makeButton("StartButton", e -> getStage().setScene(new LoadMenu(getStage(), gameFolderPath).display()), true),
				getFactory().makeButton("Highscores", e -> getStage().setScene(new HighscoreMenu(getStage(), gameFolderPath).display()), true),
				getFactory().makeButton("AchievementsButton", e -> getStage().setScene(new AchievementsMenu(getStage(), gameFolderPath).display()), true),
				getFactory().makeButton("OptionsButton", e -> getStage().setScene(new OptionsMenu(getStage(), gameFolderPath).display()), true),
				getFactory().makeButton("InfoButton", e -> getStage().setScene(new InfoMenu(getStage(), gameFolderPath).display()), true),
				getFactory().makeButton("ExitButton", e -> getStage().close(), true));
		
		this.setCenter(menu);
		menu.setAlignment(Pos.CENTER);
		menu.maxWidthProperty().bind(getStage().widthProperty().multiply(0.3));
	}

}
