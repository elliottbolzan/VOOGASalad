package data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import engine.entities.Entity;
import engine.entities.entities.AchievementEntity;
import engine.entities.entities.CameraEntity;
import engine.entities.entities.BackgroundEntity;
import engine.game.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import player.score.Score;

/**
 * @author Elliott Bolzan (Modified by Jesse Yue, Matthew Barbano, Jay Doherty)
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
	private List<Entity> achievements;
	private ObservableList<Score> highscores;
	private List<Score> highscoresBase;
	private ObservableList<String> saveStates;
	private boolean clockGoingDown=false;
	private double currentTime;
	private Set<Integer> unlockedLevels;
	private int numberOfLives;
	

	/**
	 * Returns an empty game object, with default values pre-loaded.
	 */
	public Game() {
		// TODO Load these from a properties file.
		name = "Game";
		levels = new ArrayList<Level>();
		defaults = new ArrayList<Entity>();
		songPath = "";
		info = "";
		achievements = new ArrayList<Entity>();
		highscores = FXCollections.observableList(addDefaults());
		saveStates = FXCollections.observableArrayList();
		unlockedLevels= new HashSet<Integer>();
		unlockedLevels.add(1);
	}

	public void setSaves(ObservableList<String> saves) {
		saveStates = saves;
	}

	public ObservableList<String> getSaves() {
		return saveStates;
	}

	/**
	 * Create a deep copy of List<Level> by copying clones of the entities in
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
		achievements = cloneDefaults().stream().filter(s -> s instanceof AchievementEntity).collect(Collectors.toList());
		return cloneOfLevels;
	}

	public Level cloneLevel(Level level) {
		Level cloneOfLevel = new Level();
		for (Entity entity : level.getEntities()) {
			cloneOfLevel.addEntity(entity.clone());
		}
		cloneOfLevel.setCamera((CameraEntity) level.getCamera().clone());
		cloneOfLevel.setBackground((BackgroundEntity) level.getBackground().clone());
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
		// return Collections.unmodifiableList(levels);
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
	
	public void setUnlockedLevels(Set<Integer> inputUnlockedLevels){
		unlockedLevels=inputUnlockedLevels;
	}
	
	public void setNumberOfLives(int inputNumberOfLives){
		numberOfLives=inputNumberOfLives;
	}
	
	public Set<Integer> getUnlockedLevels(){
		return unlockedLevels;
	}
	public int getNumberOfLives(){
		return numberOfLives;
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

	public List<Entity> getAchievements() {
		return achievements;
	}

	public void setAchievements(List<Entity> achievements) {
		this.achievements = achievements;
	}
	
	public List<Entity> cloneAchievements() {
		return achievements.stream().map(s -> s.clone()).collect(Collectors.toList());
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
	
	public void setCurrentTime(double inputCurrentTime){
		currentTime = inputCurrentTime;
	}
	
	public void setClockGoingDown(boolean inputClockGoingDown){
		clockGoingDown = inputClockGoingDown;
	}

	public double getCurrentTime(){
		return currentTime;
	}
	
	public boolean getClockGoingDown(){
		return clockGoingDown;
	}
	
	public Game clone() {
		Game cloneGame = new Game();
		cloneGame.setName(this.name);
		cloneGame.setLevels(this.cloneLevels());
		cloneGame.setDefaults(this.cloneDefaults());
		cloneGame.setSongPath(this.songPath);
		cloneGame.setInfo(this.info);
		cloneGame.setClockGoingDown(this.clockGoingDown);
		cloneGame.setCurrentTime(this.currentTime);
		// TODO: clone scores
		cloneGame.setAchievements(this.cloneAchievements());
		return cloneGame;
	}
}