package player;

import engine.game.timer.TimerManager;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Score {
	private IntegerProperty rank;
	private StringProperty score;
	private StringProperty time;
	private TimerManager manager;
	
	public Score(int rank){
		this(rank, "000000", new TimerManager(0.0, false));
	}
	
	public Score(String score, TimerManager time){
		this(0, score, time);
	}
	
	public Score(int rank, String score, TimerManager time){
		manager = time;
		this.rank = new SimpleIntegerProperty(rank);
		this.score = new SimpleStringProperty(score);
		this.time = new SimpleStringProperty(time.toString());
	}
	
	public IntegerProperty rankProperty(){
		return rank;
	}

	public void setRank(int value){
		rankProperty().set(value);
	}
	
	public int getRank(){
		return rankProperty().get();
	}
	
	public StringProperty scoreProperty(){
		return score;
	}
	
	public void setScore(String score){
		scoreProperty().set(score);
	}
	
	public String getScore(){
		return scoreProperty().get();
	}
	
	public StringProperty timeProperty(){
		return time;
	}
	
	public void setTime(String time){
		timeProperty().set(time);
	}
	
	public String getTime(){
		return timeProperty().get();
	}
	
	public int getTimeValue(){
		return manager.getMilliseconds();
	}
}
