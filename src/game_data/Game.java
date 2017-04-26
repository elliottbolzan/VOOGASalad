package game_data;

import java.util.ArrayList;
import java.util.List;
import engine.Entity;
import engine.entities.CameraEntity;
import engine.game.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import player.score.Score;

/**
 * @author Elliott Bolzan (Modified by Jesse Yue, Matthew Barbano)
 * 
 *         This class represents a Game. It is designed to be shared through
 *         submodules: the GameData, Game Authoring Environment, Game Player and
 *         Game Engine all can make use of this class. It encapsulates levels,
 *         settings, and default Entities.
 */
public class Game {
	private String name;
	private List<Level> levels;
	private List<Entity> defaults;
	private String songPath;
	private String info;
	private String achievements;
	private ObservableList<Score> scores;
	private List<Score> scoresBase;
	private boolean isTestGame = false;

	/**
	 * Returns an empty game object, with default values pre-loaded.
	 */
	public Game() {
		//TODO Load these from a properties file.
		name = "Game";
		levels = new ArrayList<Level>();
		defaults = new ArrayList<Entity>();
		songPath = "";
		info = "Information about game";
		achievements = "";
		scores = FXCollections.observableList(addDefaults());
	}

	/**
	 * Create a deepcopy of List<Level> by copying clones of the entities in
	 * each constituent Level. Uses GameObject's clone() method to accomplish
	 * this.
	 * 
	 * @return the clone of levels
	 */
	public List<Level> cloneLevels() {
		List<Level> cloneOfLevels = new ArrayList<Level>();
		for (Level level : levels) {
			cloneOfLevels.add(cloneLevel(level));
		}
		return cloneOfLevels;
	}

	public Level cloneLevel(Level level) {
		Level cloneOfLevel = new Level();
		for (Entity entity : level.getEntities()) {
			cloneOfLevel.addEntity(entity.clone());
		}
		cloneOfLevel.setCamera((CameraEntity) level.getCamera().clone());
		return cloneOfLevel;
	}

	public List<Entity> cloneDefaults() {
		List<Entity> cloneOfDefaults = new ArrayList<Entity>();
		for (Entity entity : this.defaults) {
			cloneOfDefaults.add(entity.clone());
		}
		return cloneOfDefaults;
	}

	/**
	 * @return the game's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the game's name.
	 * 
	 * @param name
	 *            the new name for the game.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the game's levels.
	 */
	public List<Level> getLevels() {
		return levels;
		//return Collections.unmodifiableList(levels);
	}

	/**
	 * Set the game's levels.
	 * 
	 * @param levels
	 *            the new levels for the game.
	 */
	public void setLevels(List<Level> levels) {
		this.levels = levels;
	}

	/**
	 * @return the game's default Entities.
	 */
	public List<Entity> getDefaults() {
		return defaults;
	}

	/**
	 * Set the game's default Entities.
	 * 
	 * @param defaults
	 *            the new default Entities.
	 */
	public void setDefaults(List<Entity> defaults) {
		this.defaults = defaults;
	}

	/**
	 * @return the path to the game's song.
	 */
	public String getSongPath() {
		return songPath;
	}

	/**
	 * Set the game's song path.
	 * 
	 * @param songPath
	 *            the new song path for the game.
	 */
	public void setSongPath(String songPath) {
		this.songPath = songPath;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getAchievements() {
		return achievements;
	}

	public void setAchievements(String achievements) {
		this.achievements = achievements;
	}

	/**
	 * Add a new highscore
	 * 
	 * @param score
	 *            the score when game ended
	 * @param time
	 *            the time remaining when game ended
	 */
	public void setScore(String score, String time, int timeValue, String name) {
		for (int i = 0; i < scoresBase.size(); i++) {
			if (isHighscore(score, timeValue, i)) {
				shiftScores(i, score, time, name);
				break;
			}
		}
	}

	/**
	 * 
	 * @param score
	 * @param timeValue
	 * @param i
	 * @returns boolean for if the new score is a highscore
	 */
	public boolean isHighscore(String score, int timeValue, int i) {
		if (Integer.parseInt(score) > Integer.parseInt(scoresBase.get(i).getScore())) {
			return true;
		} else {
			return Integer.parseInt(score) == Integer.parseInt(scoresBase.get(i).getScore())
					&& timeValue > scoresBase.get(i).getTimeValue();
		}
	}

	private void shiftScores(int i, String score, String time, String name) {
		// Shift scores down
		for (int j = i; j < scoresBase.size() - 1; j++) {
			scoresBase.get(j + 1).setScore(scoresBase.get(j).getScore());
			scoresBase.get(j + 1).setTime(scoresBase.get(j).getTime());
		}
		// Replace score
		scoresBase.get(i).setScore(score);
		scoresBase.get(i).setTime(time);
		scoresBase.get(i).setName(name);
	}

	/**
	 * 
	 * @return the list of highscores
	 */
	public ObservableList<Score> getScores() {
		return scores;
	}

	private List<Score> addDefaults() {
		scoresBase = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			scoresBase.add(new Score(i));
		}

		return scoresBase;
	}

	/**
	 * 
	 * @returns if the game is a test game
	 */
	public boolean isTestGame() {
		return isTestGame;
	}

	/**
	 * sets if this game is a test game
	 * 
	 * @param value
	 */
	public void setTestGame(boolean value) {
		isTestGame = value;
	}

	public Game clone() {
		Game cloneGame = new Game();
		cloneGame.setName(this.name);
		cloneGame.setLevels(this.cloneLevels());
		cloneGame.setDefaults(this.cloneDefaults());
		cloneGame.setSongPath(this.songPath);
		cloneGame.setInfo(this.info);
		cloneGame.setAchievements(this.achievements);
		// TODO: clone scores
		cloneGame.setTestGame(this.isTestGame);
		return cloneGame;
	}
}