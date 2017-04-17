package player;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends AbstractMenu{

	public MainMenu(Stage stage, Loader loader) {
		super(stage, loader, "PlayerTitle");	
		setupScene();
		stage.setScene(display());	
		setTitle(getLoader().loadGame().getName());
		backButton().setOpacity(0.0);
		stage.show();
	}
	
	public MainMenu(Loader loader){
		this(new Stage(), loader);
	}
	
	private void setupScene(){
		VBox menu = new VBox(8);

		menu.getChildren().addAll(this.getFactory().makeButton("StartButton", e -> getStage().setScene(new LoadMenu(getStage(), getLoader()).display()), true),
				getFactory().makeButton("Highscores", e -> getStage().setScene(new HighscoreMenu(getStage(), getLoader()).display()), true),
				getFactory().makeButton("AchievementsButton", e -> getStage().setScene(new AchievementsMenu(getStage(), getLoader()).display()), true),
				getFactory().makeButton("OptionsButton", e -> getStage().setScene(new OptionsMenu(getStage(), getLoader()).display()), true),
				getFactory().makeButton("InfoButton", e -> getStage().setScene(new InfoMenu(getStage(), getLoader()).display()), true),
				getFactory().makeButton("ExitButton", e -> getStage().close(), true));
		
		this.setCenter(menu);
		menu.setAlignment(Pos.CENTER);
		menu.maxWidthProperty().bind(getStage().widthProperty().multiply(0.3));
	}

}
