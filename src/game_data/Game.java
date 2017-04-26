package game_data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import engine.Entity;
import engine.entities.AchievementEntity;
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
	private String backPath;
	private String info;
	private Collection<Entity> achievements;
	private ObservableList<Score> highscores;
	private List<Score> highscoresBase;
	private boolean isTestGame = false;

	/**
	 * Returns an empty game object, with default values pre-loaded.
	 */
	public Game() {
		// Load these from a properties file.
		name = "Game";
		levels = new ArrayList<Level>();
		defaults = new ArrayList<Entity>();
		songPath = "";
		setBackPath("");
		setInfo("Information about game");
		achievements = new ArrayList<Entity>();
		highscores = FXCollections.observableList(addDefaults());
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
	}

	/**
	 * Set the game's levels.
	 * 
	 * @param levels
	 *            the new levels for the game.
	 */
	public void setLevels(List<Level> levels) {
		this.levels = levels;
		this.setAchievements(levels);
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

	public String getBackPath() {
		return backPath;
	}

	public void setBackPath(String backPath) {
		this.backPath = backPath;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Collection<Entity> getAchievements() {
		return achievements;
	}
	
	public void setAchievements(Collection<Level> levels){
		achievements = new ArrayList<>();
		for(Level level : levels){
			achievements.addAll(level.getEntities().stream().filter(s -> s.getClass().equals(AchievementEntity.class)).collect(Collectors.toList()));
		}
	}

	/**
	 * Add a new highscore
	 * 
	 * @param score
	 *            the score when game ended
	 * @param time
	 *            the time remaining when game ended
	 */
	public void setHighscores(String score, String time, int timeValue, String name) {
		for (int i = 0; i < highscoresBase.size(); i++) {
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
		if (Integer.parseInt(score) > Integer.parseInt(highscoresBase.get(i).getScore())) {
			return true;
		} else {
			return Integer.parseInt(score) == Integer.parseInt(highscoresBase.get(i).getScore())
					&& timeValue > highscoresBase.get(i).getTimeValue();
		}
	}

	private void shiftScores(int i, String score, String time, String name) {
		// Shift scores down
		for (int j = i; j < highscoresBase.size() - 1; j++) {
			highscoresBase.get(j + 1).setScore(highscoresBase.get(j).getScore());
			highscoresBase.get(j + 1).setTime(highscoresBase.get(j).getTime());
		}
		// Replace score
		highscoresBase.get(i).setScore(score);
		highscoresBase.get(i).setTime(time);
		highscoresBase.get(i).setName(name);
	}

	/**
	 * 
	 * @return the list of highscores
	 */
	public ObservableList<Score> getHighscores() {
		return highscores;
	}

	private List<Score> addDefaults() {
		highscoresBase = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			highscoresBase.add(new Score(i));
		}

		return highscoresBase;
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
		cloneGame.setBackPath(this.backPath);
		cloneGame.setInfo(this.info);
		// TODO: clone scores
		cloneGame.setTestGame(this.isTestGame);
		return cloneGame;
	}
}