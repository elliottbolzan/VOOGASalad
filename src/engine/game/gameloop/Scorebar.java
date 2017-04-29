package engine.game.gameloop;

import engine.entities.Entity;
import engine.entities.entities.CharacterEntity;
import engine.game.LevelManager;
import engine.game.timer.TimerManager;
import game_data.Game;

/**
 * Contains information displayed on the Scorebar.
 * 
 * @author Jay Doherty
 * @author Matthew Barbano
 *
 */
public class Scorebar {
	
	private LevelManager levelManager;
	private TimerManager timerManager;
	private int score;
	private Game game;

	public Scorebar(Game game) {
		this.timerManager = null;
		this.game = game;
		score = 0;
		levelManager = new LevelManager(game, null, null);
	}
	
	public void setLevelManager(LevelManager levelManager) {
		this.levelManager = levelManager;
	}

	public void resetTimerManager() {
		timerManager.reset();
	}

	public String getTime() {
		return timerManager.toString();
	}

	public int getTimeValue() {
		return timerManager.getMilliseconds();
	}

	public TimerManager getTimerManager() {
		return timerManager;
	}

	public void setTimerManager(TimerManager timerManager) {
		this.timerManager = timerManager;
	}

	public int getLives() {
		for(Entity entity : levelManager.getCurrentLevel().getEntities()){
			 if(entity instanceof CharacterEntity){
				  return ((CharacterEntity) entity).getLives();
			 }
		}
		return -1;
	}

	public void setLives(int lives) {
		for(Entity entity : levelManager.getCurrentLevel().getEntities()){
			 if(entity instanceof CharacterEntity){
				  ((CharacterEntity) entity).setLives(lives);
			 }
		}
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

	public int getLevel() {
		return levelManager.getLevelNumber();
	}

	public void saveFinalScore(String name) {
		// TODO : game data
		// game.getHighScores();
		// check if this score should be added
		// game.setHighScores();
		game.setHighscores(getScore(), getTime(), getTimeValue(), name);
	}

	private String convertScore(int score) {
		return String.format("%06d", score);
	}

	public boolean isHighscore() {
		return game.isHighscore(getScore(), getTimeValue(), 9);
	}
}
