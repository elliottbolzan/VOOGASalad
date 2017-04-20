package engine.game.gameloop;

import engine.GameInfo;
import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.util.Duration;

/**
 * Screen class deals more with transistions between Screens and Timeline. Specific step algorithm in
 * StepStrategy subclasses in the Strategy Design Pattern (composition).
 * 
 * @author Matthew Barbano
 */
public class Screen{
	public static final int FRAME_TIME_MILLISECONDS = 10;
	private StepStrategy currentStepStrategy;
	private Timeline timeline;
	
	public Screen(StepStrategy currentStepStrategy, LevelManager levelManager, Scene gameScene, GraphicsEngine graphicsEngine, GameInfo info){
		info.setCurrentScreen(this);
		this.currentStepStrategy = currentStepStrategy;
		setupTimeline();
		currentStepStrategy.setup(levelManager, gameScene, this, graphicsEngine, info);
	}
	
	private void setupTimeline(){
		// From ExampleBounce.java
		KeyFrame frame = new KeyFrame(Duration.millis(FRAME_TIME_MILLISECONDS), e -> step());
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(frame);
	}
	
	public void start(){
		timeline.play();
	}
	
	public void pause(){
		timeline.pause();
	}
	
	private void step(){
		currentStepStrategy.step();
	}
	
	public StepStrategy getCurrentStepStrategy(){
		return currentStepStrategy;
	}
	
	public Timeline getTimeline(){
		return timeline;
	}
}