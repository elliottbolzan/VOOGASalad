package player;

import java.util.ResourceBundle;

import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class Overlay extends BorderPane{
	private ResourceBundle resources = ResourceBundle.getBundle("resources/Player");
	
	private ToolBar top;
	private ToolBar bottom;
	private Label score;
	private Label lives;
	private Label level;
	private Label time;

	public Overlay(){
		setup();
		
	}
	
	private void setup(){
		top = new ToolBar();
		bottom = new ToolBar();
		Label scoreLabel = new Label(resources.getString("Score"));
		ImageView heartLabel = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(resources.getString("Heart"))));
		Label levelLabel = new Label(resources.getString("Level"));
		Label timeLabel = new Label(resources.getString("Time"));
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
