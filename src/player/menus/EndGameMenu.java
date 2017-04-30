package player.menus;

import java.util.ResourceBundle;

import data.Game;
import engine.game.gameloop.Scorebar;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import player.MediaManager;
import polyglot.Polyglot;

public class EndGameMenu extends AbstractMenu {

	private Scorebar scorebar;
	private MediaManager mediaManager;
	
	public EndGameMenu(Stage stage, Game game, MediaManager mediaManager, Polyglot polyglot, ResourceBundle IOResources, Scorebar scorebar) {
		super(stage, game, mediaManager, "HighscoresTitle", polyglot, IOResources);
		this.scorebar = scorebar;
		this.mediaManager = mediaManager;
	}

	@Override
	public void addElements() {
		VBox container = new VBox(30);
		Label congrats = new Label("New Highscore!");
		congrats.scaleXProperty().bind(this.getStage().widthProperty().divide(congrats.widthProperty()).divide(2));
		congrats.scaleYProperty().bind(congrats.scaleXProperty());
		
		TextField enterName = new TextField();
		enterName.setMaxWidth(this.getStage().getWidth()/2);
		enterName.setPromptText("Your name here");
		
		Button toHighscores = new Button("Continue");
		toHighscores.setOnAction(e -> {
			scorebar.saveFinalScore(enterName.getText());
			this.getStage().setScene(new HighscoreMenu(this.getStage(), this.getGame(), mediaManager, this.getPolyglot(), this.getResources()).createScene());
		});

		container.getChildren().addAll(congrats, enterName, toHighscores);
		container.setAlignment(Pos.CENTER);
		this.setCenter(container);
	}
}
