package engine.game;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import engine.entities.Entity;
import engine.entities.entities.AchievementEntity;
import engine.game.gameloop.Scorebar;
import engine.game.gameloop.Screen;
import engine.game.gameloop.StepStrategy;
import engine.game.selectiongroup.ListSG;
import engine.game.selectiongroup.SelectionGroup;
import engine.game.timer.TimerManager;
import game_data.Game;

/**
 * Holds all the levels in the current game and allows for game-wide behavior.
 * 
 * IMPORTANT NOTE TO ANYONE USING THIS CLASS: When using the methods in this
 * class, levels are one-indexed (likely the only information anyone else will
 * need to know). If for some reason anyone needs to use SelectionGroup, that is
 * zero-indexed.
 * 
 * @author Matthew Barbano
 *
 */
public class LevelManager {
	private SelectionGroup<Level> levels; // zero-indexed
	private SelectionGroup<Level> levelsInInitialState; // zero-indexed
	private Set<Integer> unlockedLevelNumbers; // one-indexed
	private int currentLevel; // one-indexed
	private final Game game;
	private Screen currentScreen;
	private StepStrategy currentStepStrategy;
	private boolean levelSelectionScreenMode;
	private Scorebar scorebar;

	public LevelManager(Game game, StepStrategy currentStepStrategy, Scorebar scorebar) {
		levels = new ListSG<>();
		levelsInInitialState = new ListSG<>();
		unlockedLevelNumbers = new HashSet<>();
		currentLevel = 1;
		this.game = game;
		this.currentStepStrategy = currentStepStrategy;
		this.levelSelectionScreenMode = true;
		this.scorebar = scorebar;
	}
	
	// TODO Call from GAE with small checkbox, or similar
	public boolean getLevelSelectionScreenMode() {
		return levelSelectionScreenMode;
	}

	public void setLevelSelectionScreenMode(boolean levelSelectionScreenMode) {
		this.levelSelectionScreenMode = levelSelectionScreenMode;
	}

	public Screen getCurrentScreen() {
		return currentScreen;
	}

	public void setCurrentScreen(Screen currentScreen) {
		this.currentScreen = currentScreen;
	}

	public StepStrategy getCurrentStepStrategy() {
		return currentStepStrategy;
	}

	public void setCurrentStepStrategy(StepStrategy currentStepStrategy) {
		this.currentStepStrategy = currentStepStrategy;
	}

	/**
	 * External Engine API. Needed for authoring.
	 * 
	 * @param level
	 * @return
	 */
	public void addLevel(Level level) {
		levels.add(level);
	}

	/**
	 * External Engine API. Needed for gameplay.
	 * 
	 * @return
	 */
	public Level getCurrentLevel() {
		return levels.get(currentLevel - 1);
	}

	/**
	 * Returns true if in range and successfully set level. Otherwise, returns
	 * false and currentLevel remains unchanged.
	 * 
	 * @param currentLevel
	 * @return
	 */
	public boolean setLevelNumber(int currentLevel) {
		if (levelNumberInGame(currentLevel)) {
			this.currentLevel = currentLevel;
		}
		return levelNumberInGame(currentLevel);
	}

	public boolean levelNumberInGame(int queriedLevel) {
		return queriedLevel >= 1 && queriedLevel <= levels.size();
	}

	public int getLevelNumber() {
		return currentLevel;
	}

	/**
	 * Since never save levels' state during gameplay, can call this method at
	 * any point during game loop to get levels' initial states.
	 */

	// Call once at beginning of the game
	public void loadAllSavedLevels() {
		// levels.removeAll();
		List<Entity> achievements = game.getDefaults().stream().filter(s -> s instanceof AchievementEntity)
				.collect(Collectors.toList());
		//game.setAchievements(achievements);
		List<Level> cloneLevels = game.cloneLevels();
		cloneLevels.forEach(s -> s.addEntities(achievements));
		levelsInInitialState.addAll(cloneLevels);
		List<Level> tempLevels = game.getLevels();
		tempLevels.forEach(s -> s.addEntities(achievements));
		levels.addAll(game.getLevels());
		scorebar.setTimerManager(new TimerManager(game.getCurrentTime(), game.getClockGoingDown()));
		//unlockedLevelNumbers = game.getUnlockedLevels();  //<---
		//scorebar.setLives(game.getNumberOfLives());  //<---
	}

	// Call when start up a level (first time AND after die)
	public void resetCurrentLevel() {
		levels.set(currentLevel - 1, game.cloneLevel(levelsInInitialState.get(currentLevel - 1)));
	}

	public SelectionGroup<Level> getLevels() {
		return levels;
	}

	public void addUnlockedLevel(int currentLevel) {
		if (levelNumberInGame(currentLevel)) {
			unlockedLevelNumbers.add(currentLevel);
		}
	}

	public void clearUnlockedLevels() {
		unlockedLevelNumbers.removeAll(unlockedLevelNumbers);
	}

	public Set<Integer> getUnlockedLevelNumbers() {
		return unlockedLevelNumbers;
	}

	public Game getGame() {
		return game;
	}
}
