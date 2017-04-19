package player;

import java.util.ResourceBundle;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import polyglot.Polyglot;

public class MainMenu extends AbstractMenu {

	private Polyglot polyglot;
	private ResourceBundle IOResources;

	public MainMenu(Stage stage, Loader loader, Polyglot polyglot, ResourceBundle IOResources) {
		super(stage, loader, "PlayerTitle", polyglot, IOResources);
		this.polyglot = polyglot;
		this.IOResources = IOResources;
		setupScene();
		stage.setScene(display());
		setTitleFixed(getLoader().loadGame().getName());
		backButton().setOpacity(0.0);
		stage.show();
	}

	public MainMenu(Loader loader, Polyglot polyglot, ResourceBundle IOResources) {
		this(new Stage(), loader, polyglot, IOResources);
	}

	private void setupScene() {
		VBox menu = new VBox(8);

		menu.getChildren().addAll(
				this.getFactory().makeButton("StartButton",
						e -> getStage()
								.setScene(new LoadMenu(getStage(), getLoader(), polyglot, IOResources).display()),
						true),
				getFactory().makeButton("Highscores",
						e -> getStage()
								.setScene(new HighscoreMenu(getStage(), getLoader(), polyglot, IOResources).display()),
						true),
				getFactory().makeButton("AchievementsButton",
						e -> getStage().setScene(
								new AchievementsMenu(getStage(), getLoader(), polyglot, IOResources).display()),
						true),
				getFactory().makeButton("OptionsButton",
						e -> getStage()
								.setScene(new OptionsMenu(getStage(), getLoader(), polyglot, IOResources).display()),
						true),
				getFactory().makeButton("InfoButton",
						e -> getStage()
								.setScene(new InfoMenu(getStage(), getLoader(), polyglot, IOResources).display()),
						true),
				getFactory().makeButton("ExitButton", e -> getStage().close(), true));

		this.setCenter(menu);
		menu.setAlignment(Pos.CENTER);
		menu.maxWidthProperty().bind(getStage().widthProperty().multiply(0.3));
	}

}
