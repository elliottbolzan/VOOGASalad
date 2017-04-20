package player.score;

import java.util.ResourceBundle;

import javafx.beans.binding.StringBinding;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import polyglot.Case;
import polyglot.Polyglot;

/**
 * 
 * @author Jesse
 *
 */
public class Overlay extends BorderPane{
	private Polyglot polyglot;
	private ResourceBundle IOResources;
	
	private Label score;
	private Label lives;
	private Label level;
	private Label time;

	public Overlay(Polyglot polyglot, ResourceBundle IOResources){
		this.polyglot = polyglot;
		this.IOResources = IOResources;
		setup();	
	}
	
	private void setup(){
		score = new Label();
		lives = new Label();
		level = new Label();
		time = new Label();
		
		HBox container = new HBox(100);
		container.setAlignment(Pos.CENTER);
		container.getChildren().addAll(makeLabelBox(new Label(), polyglot.get("Score", Case.UPPER), score),
				makeImageBox(new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(IOResources.getString("HeartPath")))), lives),
				makeLabelBox(new Label(), polyglot.get("Level", Case.UPPER), level),
				makeLabelBox(new Label(), polyglot.get("Time", Case.UPPER), time));
		this.setTop(container);
	}
	
	private VBox makeLabelBox(Label title, StringBinding titleValue, Label value){
		title.textProperty().bind(titleValue);
		VBox box = new VBox(5);
		box.getChildren().addAll(title, value);
		box.setAlignment(Pos.CENTER);
		return box;
	}
	
	private VBox makeImageBox(ImageView image, Label value){
		VBox box = new VBox(5);
		box.getChildren().addAll(image, value);
		box.setAlignment(Pos.CENTER);
		return box;
	}
	
	public Pane display(){
		return this;
	}
	
	public void setScore(String newScore){
		score.setText(newScore);
	}
	
	public void setLives(String newLives){
		lives.setText(newLives);
	}
	
	public void setLevel(String newLevel){
		level.setText(newLevel);
	}
	
	public void setTime(String newTime){
		time.setText(newTime);
	}
}
