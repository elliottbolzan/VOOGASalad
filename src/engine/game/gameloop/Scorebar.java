package engine.game.gameloop;

import engine.game.timer.TimerManager;
import game_data.Game;
import player.score.Score;

/**
 * Contains information displayed on the Scorebar.
 * 
 * @author Jay Doherty
 * @author Matthew Barbano
 *
 */
public class Scorebar {
	private TimerManager timerManager; // restart it every time restart new
										// level! (perhaps in another class
										// calling this class' methods
	private int lives; // immutable except by Character Entity - TODO extension
						// sprint - get rid of this duplication of lives in
						// CharacterEntity and here by allowing GAE to set
						// Scorebar values too! (also consider multiplayer)
	private int score;
	private int level;
	private Game game;
	

	public Scorebar(Game game) {
		this.timerManager = new TimerManager(120, false);
		this.game = game;
		lives = 5;
		score = 0;
		level = 1;
	}

	public void resetTimerManager() {
		timerManager.reset();
	}

	public String getTime() {
		return timerManager.toString();
	}
	
	public int getTimeValue(){
		return timerManager.getMilliseconds();
	}

	public TimerManager getTimerManager() {
		return timerManager;
	}

	public void setTimerManager(TimerManager timerManager) {
		this.timerManager = timerManager;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public String getScore() {
		return convertScore(score);
	}

	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * 
	 * @param scoreChange
	 *            amount of points by which the current score will change
	 */
	public void updateScore(int scoreChange) {
		this.score += scoreChange;
	}

	
	public int getLevel(){
		return level;
	}
	
	public void setLevel(int level){
		this.level = level;
	}
	
	public void saveFinalScore(String name) {
		//TODO : game data
		//game.getHighScores();
		//check if this score should be added
		//game.setHighScores();
		game.setScore(getScore(), getTime(), getTimeValue(), name);
	}
	
	private String convertScore(int score){
		return String.format("%06d", score);
	}
	
	public boolean isHighscore(){
		return game.isHighscore(getScore(), getTimeValue(), 9);
	}
}
