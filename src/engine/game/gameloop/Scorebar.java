package engine.game.gameloop;

import engine.game.timer.TimerManager;

/**
 * Contains information displayed on the Scorebar.
 * 
 * @author Jay Doherty
 * @author Matthew Barbano
 *
 */
public class Scorebar {
	private TimerManager timerManager;   //restart it every time restart new level! (perhaps in another class calling this class' methods
	private int lives;   //immutable except by Character Entity - TODO extension sprint - get rid of this duplication of lives in CharacterEntity and here by allowing GAE to set Scorebar values too! (also consider multiplayer)
	private int score;
	private int level;
	
	public Scorebar() {
		timerManager = new TimerManager(120, false);
		lives = 5;
		score = 0;
		level = 0;
	}
	
	public void resetTimerManager(){
		 timerManager.reset();
	}
	
	public String getTime() {
		return timerManager.toString();
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public int getLevel(){
		return level;
	}
	
	public void setLevel(int level){
		this.level = level;
	}
}
