package player;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Score {
	private IntegerProperty rank;
	private StringProperty score;
	private StringProperty time;
	
	public Score(int rank){
		this(rank, "0000", "00:00:00");
	}
	
	public Score(String score, String time){
		this(0, score, time);
	}
	
	public Score(int rank, String score, String time){
		this.rank = new SimpleIntegerProperty(rank);
		this.score = new SimpleStringProperty(score);
		this.time = new SimpleStringProperty(time);
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
}
