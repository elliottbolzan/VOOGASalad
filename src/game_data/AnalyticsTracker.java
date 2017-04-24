package game_data;

public class AnalyticsTracker {

	private float loggedScore;
	private boolean hasWon;
	private boolean hasPlayed;
	
	public void hasWon(){
		hasWon=true;
	}
	public void hasLost(){
		hasWon=false;
	}
	
	public void logScore(float score){
		loggedScore = score;
	}
	public void logPlay(){
		hasPlayed= true;
	}
	
	public boolean getHasWon(){
		return hasWon;
	}
	public boolean getHasPlayed(){
		return hasPlayed;
	}
	public float getScore(){
		return loggedScore;
	}
	
}
