package player.menu;

import java.util.ResourceBundle;

import engine.entities.Entity;
import engine.entities.entities.AchievementEntity;
import engine.events.Event;
import game_data.Game;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
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
		
		pane.setContent(container);
		this.setCenter(pane);
		this.setBottom(this.makeBackButton());
		this.setInsets();
	}
	
	private HBox makeAchievementBox(Entity achievement){
		HBox container = new HBox(5);
		container.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));
		container.prefWidthProperty().bind(this.widthProperty());
		
		ImageView image= new ImageView();
		if(achievement.getImagePath() != null){
			image.setImage(new Image(achievement.getImagePath()));
		}
		image.setPreserveRatio(true);
		image.setFitWidth(100);
		image.setFitHeight(100);
		StackPane pane = new StackPane();
		pane.getChildren().addAll(image);
		pane.setPrefSize(100, 100);
		
		container.getChildren().addAll(pane, makeLabelBox(achievement));
		
		return container;
	}
	
	private VBox makeLabelBox(Entity achievement){
		VBox box = new VBox(5);
		box.setFillWidth(true);
		Label name = new Label(achievement.getName());
		name.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
		Label description = new Label();
		if(achievement.getDisplayDescription() != null){
			description.setText((String) achievement.getParam("Description"));
		}
		
		
		box.getChildren().addAll(name, description, makeProgressBox(achievement));
		
		return box;
	}
	
	private VBox makeProgressBox(Entity achievement){	
		VBox progressBox = new VBox(5);
		for(Event event : ((AchievementEntity) achievement).getPercentCompleted().keySet()){
			HBox container = new HBox(10);
			Label eventName = new Label(event.getDisplayName());
			
			ProgressBar progress = new ProgressBar();
			progress.progressProperty().bind(((AchievementEntity) achievement).getPercentCompleted().get(event));
			ProgressIndicator indicator = new ProgressIndicator();
			indicator.progressProperty().bind(progress.progressProperty());
			container.getChildren().addAll(eventName, progress, indicator);
			progressBox.getChildren().add(container);
		}

		return progressBox;
	}
	
}
