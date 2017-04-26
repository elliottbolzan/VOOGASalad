package player.menu;

import java.util.ResourceBundle;

import engine.Entity;
import engine.entities.AchievementEntity;
import game_data.Game;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import player.MediaManager;
import polyglot.Polyglot;

public class AchievementsMenu extends AbstractMenu {

	public AchievementsMenu(Stage stage, Game game, MediaManager mediaManager, Polyglot polyglot, ResourceBundle IOResources) {
		super(stage, game, mediaManager, "AchievementsTitle", polyglot, IOResources);
	}

	@Override
	public void addElements() {
		ScrollPane pane = new ScrollPane();
		VBox container = new VBox(10);
		for(Entity entity : this.getGame().getAchievements()){
			container.getChildren().add(makeAchievementBox(entity));
		}
		//Test
		AchievementEntity achievement = new AchievementEntity();
		achievement.setName("Winner winner chicken dinner");
		container.getChildren().add(makeAchievementBox(achievement));
		//
		
		pane.setContent(container);
		this.setCenter(pane);
	}
	
	private HBox makeAchievementBox(Entity achievement){
		HBox container = new HBox(5);
		container.setPrefWidth(300);
		container.setMaxWidth(300);
		VBox box = new VBox(5);
		ImageView image= new ImageView();
		if(achievement.getImagePath() != null){
			image.setImage(new Image(achievement.getImagePath()));
		}
		image.setPreserveRatio(true);
		image.setFitHeight(100);
		image.setFitWidth(100);
		Label name = new Label(achievement.getName());
		Label description = new Label();
		if(achievement.getDisplayDescription() != null){
			description.setText((String) achievement.getParam("Description"));
		}
		ProgressBar progress = new ProgressBar();
		
		box.getChildren().addAll(name, description, progress);
		container.getChildren().addAll(image, box);
		
		return container;
	}
	
}
