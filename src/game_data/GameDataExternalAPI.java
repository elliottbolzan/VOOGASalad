package game_data;
import java.util.Collection;
import java.util.List;

import engine.*;
import engine.actions.Action;
import engine.entities.Entity;
import engine.events.Event;
import engine.game.Level;
import javafx.scene.Node;

public interface GameDataExternalAPI{
	
	/*
	 * Returns nodes from entities
	 */
	public Collection<Node> getNodesFromEntities(Collection<Entity> entities);
	
	
// All save methods
	/*
	 * Saves entity for specific level
	 */
	public void saveEntityForLevel(Entity entity, int level);
	
	/*
	 * Saves entire level based on list of entities from list of levels
	 */
	public void saveGame(List<Level> levels);
	
	/*
	 * Save single entity
	 */
	public void saveDefaultEntity(Entity entity);
	
	/*
	 * Save single event
	 */
	public void saveDefaultEvent(Event event);
	
	/*
	 * Save single action
	 */
	public void saveDefaultAction(Action action);
	
	/*
	 * Save component
	 */
//	public void saveComponent(Component component);
//	
//	public void setGameSong(File name);
//	
//	public void setGameOrientation(Orientation orient);
//	
//	public void setScrollingStyle(ScrollingStyle style);
	
// All load methods
	public void loadEntityForLevel(Entity entity, int level);
	public Level loadGame(String filepath);
	public void loadDefaultEntity(Entity entity);
	public void loadDefaultEvent(Event event);
	public void loadDefaultAction(Action action);
//	public void loadComponent(Component component);
//	public void getGameSong(File name);
//	public void getGameOrientation(Orientation orient);
//	public void getScrollingStyle(ScrollingStyle style);
}