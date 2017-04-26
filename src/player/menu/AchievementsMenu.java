package player.menu;

import java.util.ResourceBundle;

import engine.Entity;
import engine.entities.AchievementEntity;
import game_data.Game;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import player.MediaManager;
import polyglot.Polyglot;

/**
 * 
 * @author Jesse
 *
 */
public class AchievementsMenu extends AbstractMenu {

	public AchievementsMenu(Stage stage, Game game, MediaManager mediaManager, Polyglot polyglot, ResourceBundle IOResources) {
		super(stage, game, mediaManager, "AchievementsTitle", polyglot, IOResources);
	}

	@Override
	public void addElements() {
		ScrollPane pane = new ScrollPane();
		pane.setFitToWidth(true);
		VBox container = new VBox(10);
		container.setAlignment(Pos.CENTER);
		for(Entity entity : this.getGame().getAchievements()){
			container.getChildren().add(makeImageBox(entity));
		}
		//Test
		AchievementEntity achievement = new AchievementEntity();
		achievement.setName("Winner winner chicken dinner");
		container.getChildren().add(makeImageBox(achievement));
		//
		
		pane.setContent(container);
		this.setCenter(pane);
		this.setBottom(this.makeBackButton());
	}
	
	private HBox makeImageBox(Entity achievement){
		HBox container = new HBox(5);
		container.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));
		container.setPrefWidth(300);
		container.setMaxWidth(300);
		
		ImageView image= new ImageView();
		if(achievement.getImagePath() != null){
			image.setImage(new Image(achievement.getImagePath()));
		}
		image.setPreserveRatio(true);
		image.setFitHeight(100);
		image.setFitWidth(100);
		
		container.getChildren().addAll(image, makeAchievementBox(achievement));
		
		return container;
	}
	
	private VBox makeAchievementBox(Entity achievement){
		VBox box = new VBox(5);
		Label name = new Label(achievement.getName());
		Label description = new Label();
		if(achievement.getDisplayDescription() != null){
			description.setText((String) achievement.getParam("Description"));
		}
		ProgressBar progress = new ProgressBar();
		
		box.getChildren().addAll(name, description, progress);
		
		return box;
	}
	
}
