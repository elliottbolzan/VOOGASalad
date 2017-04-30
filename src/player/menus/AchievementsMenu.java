package player.menus;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import data.Game;
import engine.entities.Entity;
import engine.entities.entities.AchievementEntity;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import player.MediaManager;
import polyglot.Polyglot;

/**
 * 
 * @author Jesse
 *
 */
public class AchievementsMenu extends AbstractMenu {
	private Set<String> achievements;

	public AchievementsMenu(Stage stage, Game game, MediaManager mediaManager, Polyglot polyglot,
			ResourceBundle IOResources) {
		super(stage, game, mediaManager, "AchievementsTitle", polyglot, IOResources);
	}

	@Override
	public void addElements() {
		ScrollPane pane = new ScrollPane();
		pane.setFitToWidth(true);
		VBox container = new VBox(10);
		container.setAlignment(Pos.CENTER);
		achievements = new HashSet<>();
		for (Entity entity : this.getGame().getAchievements()) {
			if(!achievements.contains(entity.getName())){
				achievements.add(entity.getName());
				container.getChildren().add(makeAchievementBox(entity));
			}
			
		}

		pane.setContent(container);
		this.setCenter(pane);
		this.setBottom(this.makeBackButton());
		this.setInsets();		
	}

	private HBox makeAchievementBox(Entity achievement) {
		HBox container = new HBox(5);
		container.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));
		container.prefWidthProperty().bind(this.widthProperty());

		ImageView image = new ImageView();
		if (achievement.getImagePath() != null) {
			image.setImage(new Image(achievement.getImagePath()));
		}
		image.setPreserveRatio(true);
		image.setFitWidth(100);
		image.setFitHeight(100);
		StackPane pane = new StackPane();
		pane.getChildren().addAll(image);
		pane.setPrefSize(100, 100);

		container.getChildren().addAll(pane, makeLabelBox((AchievementEntity) achievement));

		return container;
	}

	private VBox makeLabelBox(AchievementEntity achievement) {
		VBox box = new VBox(5);
		box.setFillWidth(true);
		Label name = new Label(achievement.getName());
		name.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
		Label description = new Label();
		if (achievement.getDisplayDescription() != null) {
			description.setText((String) achievement.getParam("Description"));
		}

		box.getChildren().addAll(name, description, makeProgressBox(achievement));

		return box;
	}

	private HBox makeProgressBox(AchievementEntity achievement) {
		HBox container = new HBox(10);

		ProgressBar progress = new ProgressBar();
		progress.progressProperty().bind(achievement.getPercent());
		
		HBox percentContainer = new HBox();
		Label percent = new Label();
		percent.textProperty().bind(Bindings.convert(achievement.getPercent().multiply(100)));
		Label percentSymbol = new Label("%");
		percentContainer.getChildren().addAll(percent, percentSymbol);
		
		container.getChildren().addAll(progress, percentContainer);
		return container;
	}

}
