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
			container.getChildren().add(makeAchievementBox(entity));
		}
		//Test
		AchievementEntity achievement = new AchievementEntity();
		achievement.setName("Winner winner chicken dinner");
		container.getChildren().add(makeAchievementBox(achievement));
		//
		
		pane.setContent(container);
		this.setCenter(pane);
		this.setBottom(this.makeBackButton());
	}
	
	private HBox makeAchievementBox(Entity achievement){
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
		
		container.getChildren().addAll(image, makeLabelBox(achievement));
		
		return container;
	}
	
	private VBox makeLabelBox(Entity achievement){
		VBox box = new VBox(5);
		Label name = new Label(achievement.getName());
		Label description = new Label();
		if(achievement.getDisplayDescription() != null){
			description.setText((String) achievement.getParam("Description"));
		}
		
		
		box.getChildren().addAll(name, description, makeProgressBox(achievement));
		
		return box;
	}
	
	private HBox makeProgressBox(Entity achievement){
		HBox container = new HBox(10);
		HBox box = new HBox();
		ProgressBar progress = new ProgressBar();
		Label completed = new Label();
		Label slash = new Label("/");
		Label total = new Label();
		
		return container;
	}
	
}
